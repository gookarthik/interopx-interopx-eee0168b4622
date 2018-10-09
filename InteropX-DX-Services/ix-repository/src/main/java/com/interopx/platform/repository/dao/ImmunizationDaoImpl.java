package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.ImmunizationResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class ImmunizationDaoImpl extends AbstractDao implements ImmunizationDao {

	public ImmunizationResource saveOrUpdateImmunization(ImmunizationResource immunization) {
		getSession().saveOrUpdate(immunization);
		return immunization;
	}

	public List<ImmunizationResource> getAllImmunization() {
		List<ImmunizationResource> immunizations = getSession().createCriteria(ImmunizationResource.class).list();
		return immunizations;
	}

	public ImmunizationResource getImmunizationById(Integer id) {
		ImmunizationResource immunization = (ImmunizationResource) getSession().get(ImmunizationResource.class, id);
		return immunization;
	}
	public List<ImmunizationResource> getImmunizationResourceByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		Criteria criteria = getSession().createCriteria(ImmunizationResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(patientId!=null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	@Override
	public List<ImmunizationResource> getImmunizationsByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		Criteria criteria = getSession().createCriteria(ImmunizationResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		return criteria.list();
	}

	@Override
	public List<ImmunizationResource> getDuplicateImmunizations(Integer id, String code, Date date,
			String iXPatientId) {
		Criteria criteria = getSession().createCriteria(ImmunizationResource.class);
		criteria.add(Restrictions.ne("id", id));
		criteria.add(Restrictions.and(Restrictions.ne("interopxImmunizationId",""), Restrictions.isNotNull("interopxImmunizationId")));
		criteria.add(Restrictions.sqlRestriction("data->>'immCode' = '"+code+"'"));
		criteria.add(Restrictions.eq("interopxPatientId", iXPatientId));
		if(date!=null)
			criteria.add(Restrictions.sqlRestriction("(data->>'completedDate')::BIGINT ="+ String.valueOf(date.getTime())));
		return criteria.list();
	}

	@Override
	public void updateInteropXImmunizationId(Integer id, String interopXImmunizationId,
			DataProcessingStatusEnum processName, Boolean processStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update ImmunizationResource set interopx_immunization_id=:interopXImmunizationId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processName.getName()+"}', to_jsonb("+processStatus+"), true)  where id=:id");
        qry.setParameter("id",id);
        qry.setParameter("interopXImmunizationId", interopXImmunizationId);
        qry.executeUpdate();
        session.clear();
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		String sqlQuery = "update immunization set interopx_patient_id=:interopXPatientId, data_processing_status=jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where data ->> 'patientId'= :actualPatientId";
		Query qry = getSession().createSQLQuery(sqlQuery);
        qry.setParameter("interopXPatientId", interopXPatientId);
        qry.setParameter("actualPatientId", actualPatientId);
		qry.executeUpdate();
		
	}

	@Override
	public List<Object> getUniqueImmunizationsByInteropXPatientId(String interopXPatientId) {
		String hql = "from ImmunizationResource where id IN (select min(id) from ImmunizationResource where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

}
