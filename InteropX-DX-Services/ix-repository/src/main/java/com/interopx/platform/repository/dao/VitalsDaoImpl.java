package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.interopx.platform.repository.model.VitalSigns;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class VitalsDaoImpl extends AbstractDao implements VitalsDao {

	public VitalSigns saveOrUpdateVital(VitalSigns vitals) {
		getSession().saveOrUpdate(vitals);
		return vitals;
	}

	public List<VitalSigns> getAllVitals() {
		List<VitalSigns> vitals = getSession().createCriteria(VitalSigns.class).list();
		return vitals;
	}

	public VitalSigns getVitalById(Integer id) {
		VitalSigns vitals = (VitalSigns) getSession().get(VitalSigns.class, id);
		return vitals;
	}
	
	public List<VitalSigns> getVitalSignsByExtractionIdAndInternalPatientId(Integer etId, String patientId){
		Criteria criteria = getSession().createCriteria(VitalSigns.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(patientId!=null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	@Override
	public List<VitalSigns> getVitalsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus) {
		Criteria criteria = getSession().createCriteria(VitalSigns.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		return criteria.list();
	}

	@Override
	public void updateInteropXVitalId(Integer id, String interopXVitalId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update VitalSigns set interopx_vitals_id=:interopXVitalId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processName.getName()+"}', to_jsonb("+processStatus+"), true)  where id=:id");
        qry.setParameter("id",id);
        qry.setParameter("interopXVitalId", interopXVitalId);
        qry.executeUpdate();
        session.clear();
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		String sqlQuery = "update vital_signs set interopx_patient_id=:interopXPatientId, data_processing_status=jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where data ->> 'patientId'= :actualPatientId";
		Query qry = getSession().createSQLQuery(sqlQuery);
        qry.setParameter("interopXPatientId", interopXPatientId);
        qry.setParameter("actualPatientId", actualPatientId);
		qry.executeUpdate();
		
	}

	public List<VitalSigns> getDuplicateVitalSigns(Integer id, String code, Date date, String iXPatientId) {
		Criteria criteria = getSession().createCriteria(VitalSigns.class);
		criteria.add(Restrictions.ne("id", id));
		criteria.add(Restrictions.and(Restrictions.ne("interopxVitalId",""), Restrictions.isNotNull("interopxVitalId")));
		criteria.add(Restrictions.sqlRestriction("data->>'vitalCode' = '"+code+"'"));
		criteria.add(Restrictions.eq("interopxPatientId", iXPatientId));
		if(date!=null)
			criteria.add(Restrictions.sqlRestriction("(data->>'vitalsDate')::BIGINT ="+ String.valueOf(date.getTime())));
		return criteria.list();
	}

	@Override
	public List<Object> getUniqueVitalSignsByInteropXPatientId(String interopXPatientId) {
		String hql = "from VitalSigns where id IN (select min(id) from VitalSigns where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

}
