package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.ProcedureResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class ProcedureDaoImpl extends AbstractDao implements ProcedureDao {

	public ProcedureResource saveOrUpdateProcedure(ProcedureResource procedure) {
		getSession().saveOrUpdate(procedure);
		return procedure;
	}

	public List<ProcedureResource> getAllProcedure(){
		List<ProcedureResource> procedures = getSession().createCriteria(ProcedureResource.class).list();
		return procedures;
	}

	public ProcedureResource getProcedureById(Integer id) {
		ProcedureResource procedure = (ProcedureResource) getSession().get(ProcedureResource.class, id);
		return procedure;
	}

	public List<ProcedureResource> getProcedureByExtractionIdAndInternalPatientId(Integer etId, String patientId){
		Criteria criteria = getSession().createCriteria(ProcedureResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(patientId!=null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	@Override
	public List<ProcedureResource> getProceduresByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		Criteria criteria = getSession().createCriteria(ProcedureResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		return criteria.list();
	}

	@Override
	public List<ProcedureResource> getDuplicateProcedures(Integer id, String code, Date date, String iXPatientId) {
		Criteria criteria = getSession().createCriteria(ProcedureResource.class);
		criteria.add(Restrictions.ne("id", id));
		criteria.add(Restrictions.and(Restrictions.ne("interopxProcedureId",""), Restrictions.isNotNull("interopxProcedureId")));
		criteria.add(Restrictions.sqlRestriction("data->>'ccCptCode' = '"+code+"'"));
		criteria.add(Restrictions.eq("interopxPatientId", iXPatientId));
		if(date!=null)
			criteria.add(Restrictions.sqlRestriction("(data->>'ccDateOfService')::BIGINT ="+ String.valueOf(date.getTime())));
		return criteria.list();
	}

	@Override
	public void updateInteropXProcedureId(Integer id, String interopXProcedureId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update ProcedureResource set interopx_procedure_id=:interopXProcedureId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processName.getName()+"}', to_jsonb("+processStatus+"), true)  where id=:id");
        qry.setParameter("id",id);
        qry.setParameter("interopXProcedureId", interopXProcedureId);
        qry.executeUpdate();
        session.clear();
	}
	
	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		String sqlQuery = "update procedure set interopx_patient_id=:interopXPatientId, data_processing_status=jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where data ->> 'patientId'= :actualPatientId";
		Query qry = getSession().createSQLQuery(sqlQuery);
        qry.setParameter("interopXPatientId", interopXPatientId);
        qry.setParameter("actualPatientId", actualPatientId);
		qry.executeUpdate();
		
	}

	@Override
	public List<Object> getUniqueProceduresByInteropXPatientId(String interopXPatientId) {
		String hql = "from ProcedureResource where id IN (select min(id) from ProcedureResource where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

}
