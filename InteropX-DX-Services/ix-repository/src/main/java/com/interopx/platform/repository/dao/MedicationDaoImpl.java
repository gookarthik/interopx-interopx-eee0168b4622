package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.MedicationResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class MedicationDaoImpl extends AbstractDao implements MedicationDao {

	public MedicationResource saveOrUpdateMedication(MedicationResource medication) {
		getSession().saveOrUpdate(medication);
		return medication;
	}

	public List<MedicationResource> getAllMedication() {
		List<MedicationResource> medications = getSession().createCriteria(MedicationResource.class).list();
		return medications;
	}

	public MedicationResource getMedicationById(Integer id) {
		MedicationResource medication = (MedicationResource) getSession().get(MedicationResource.class, id);
		return medication;
	}
	
	public List<MedicationResource> getMedicationStatementsByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		Criteria criteria = getSession().createCriteria(MedicationResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(patientId!=null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	@Override
	public List<MedicationResource> getMedicationsByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		Criteria criteria = getSession().createCriteria(MedicationResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		return criteria.list();
	}

	@Override
	public List<MedicationResource> getDuplicateMedicaitons(Integer id, String code, Date date, String iXPatientId) {
		Criteria criteria = getSession().createCriteria(MedicationResource.class);
		criteria.add(Restrictions.ne("id", id));
		criteria.add(Restrictions.and(Restrictions.ne("interopxMedicationId",""), Restrictions.isNotNull("interopxMedicationId")));
		criteria.add(Restrictions.sqlRestriction("data->>'code' = '"+code+"'"));
		criteria.add(Restrictions.eq("interopxPatientId", iXPatientId));
		if(date!=null)
			criteria.add(Restrictions.sqlRestriction("(data->>'prescriptionDate')::BIGINT ="+ String.valueOf(date.getTime())));
		return criteria.list();
	}

	@Override
	public void updateInteropXMedicationId(Integer id, String interopXMedicationId,
			DataProcessingStatusEnum processName, Boolean processStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update MedicationResource set interopx_medication_id=:interopXMedicationId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processName.getName()+"}', to_jsonb("+processStatus+"), true)  where id=:id");
        qry.setParameter("id",id);
        qry.setParameter("interopXMedicationId", interopXMedicationId);
        qry.executeUpdate();
        session.clear();
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		String sqlQuery = "update medication set interopx_patient_id=:interopXPatientId, data_processing_status=jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where data ->> 'patientId'= :actualPatientId";
		Query qry = getSession().createSQLQuery(sqlQuery);
        qry.setParameter("interopXPatientId", interopXPatientId);
        qry.setParameter("actualPatientId", actualPatientId);
		qry.executeUpdate();
		
	}

	@Override
	public List<Object> getUniqueMedicationsByInteropXPatientId(String interopXPatientId) {
		String hql = "from MedicationResource where id IN (select min(id) from MedicationResource where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

}
