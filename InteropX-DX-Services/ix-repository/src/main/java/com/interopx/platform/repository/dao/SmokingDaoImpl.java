package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.SmokingResource;
import com.interopx.platform.repository.model.VitalSigns;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class SmokingDaoImpl extends AbstractDao implements SmokingDao {

	public SmokingResource saveOrUpdateSmoke(SmokingResource smoke) {
		getSession().saveOrUpdate(smoke);
		return smoke;
	}

	public List<SmokingResource> getAllSmoking() {
		List<SmokingResource> smoke = getSession().createCriteria(SmokingResource.class).list();
		return smoke;
	}

	public SmokingResource getSmokeById(Integer id) {
		SmokingResource smoke = (SmokingResource) getSession().get(SmokingResource.class, id);
		return smoke;
	}
	
	public List<SmokingResource> getSmokingResourceByExtractionIdAndInternalPatientId(Integer etId, String patientId){
		Criteria criteria = getSession().createCriteria(SmokingResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(patientId!=null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	@Override
	public List<SmokingResource> getSmokingsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus) {
		Criteria criteria = getSession().createCriteria(SmokingResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		return criteria.list();
	}

	@Override
	public void updateInteropXSmokingId(Integer id, String interopXSmokingId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update SmokingResource set interopx_smoking_id=:interopXSmokingId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processName.getName()+"}', to_jsonb("+processStatus+"), true)  where id=:id");
        qry.setParameter("id",id);
        qry.setParameter("interopXSmokingId", interopXSmokingId);
        qry.executeUpdate();
        session.clear();
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		String sqlQuery = "update smoking set interopx_patient_id=:interopXPatientId, data_processing_status=jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where data ->> 'patientId'= :actualPatientId";
		Query qry = getSession().createSQLQuery(sqlQuery);
        qry.setParameter("interopXPatientId", interopXPatientId);
        qry.setParameter("actualPatientId", actualPatientId);
		qry.executeUpdate();
		
	}

	@Override
	public List<SmokingResource> getDuplicateSmokings(Integer id, String smokingStatusCode, Date startTime,
			String interopxPatientId) {
		Criteria criteria = getSession().createCriteria(SmokingResource.class);
		criteria.add(Restrictions.ne("id", id));
		criteria.add(Restrictions.and(Restrictions.ne("interopxSmokingId",""), Restrictions.isNotNull("interopxSmokingId")));
		criteria.add(Restrictions.sqlRestriction("data->>'smokingStatusCode' = '"+smokingStatusCode+"'"));
		criteria.add(Restrictions.eq("interopxPatientId", interopxPatientId));
		if(startTime!=null)
			criteria.add(Restrictions.sqlRestriction("(data->>'startTime')::BIGINT ="+ String.valueOf(startTime.getTime())));
		return criteria.list();
	}

	@Override
	public List<Object> getUniqueSmokingsByInteropXPatientId(String interopXPatientId) {
		String hql = "from SmokingResource where id IN (select min(id) from SmokingResource where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

}
