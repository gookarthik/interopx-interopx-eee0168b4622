package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.AllergyIntolerance;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class AllergyIntoleranceDaoImpl extends AbstractDao implements AllergyIntoleranceDao {

	public AllergyIntolerance saveOrUpdateAllergy(AllergyIntolerance allergyIntolerance) {
		getSession().saveOrUpdate(allergyIntolerance);
		return allergyIntolerance;
	}

	public List<AllergyIntolerance> getAllAllergyIntolerance() {
		List<AllergyIntolerance> allergy = getSession().createCriteria(AllergyIntolerance.class).list();
		return allergy;
	}

	public AllergyIntolerance getAllergyIntoleranceById(Integer id) {
		AllergyIntolerance allergy = (AllergyIntolerance) getSession().get(AllergyIntolerance.class, id);
		return allergy;
	}

	
	public List<AllergyIntolerance> getAllergyResourceByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		Criteria criteria = getSession().createCriteria(AllergyIntolerance.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(patientId!=null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	public List<AllergyIntolerance> getAllergiesByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		Criteria criteria = getSession().createCriteria(AllergyIntolerance.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		return criteria.list();
	}

	public List<AllergyIntolerance> getDuplicateAllergies(Integer id, String code, Date date, String iXPatientId) {
		Criteria criteria = getSession().createCriteria(AllergyIntolerance.class);
		criteria.add(Restrictions.ne("id", id));
		//criteria.add(Restrictions.isNotNull("interopxAllergyIntoleranceId"));
		criteria.add(Restrictions.and(Restrictions.ne("interopxAllergyIntoleranceId",""), Restrictions.isNotNull("interopxAllergyIntoleranceId")));
		criteria.add(Restrictions.sqlRestriction("data->>'allergyCode' = '"+code+"'"));
		criteria.add(Restrictions.eq("interopxPatientId", iXPatientId));
		if(date!=null)
			criteria.add(Restrictions.sqlRestriction("(data->>'allergyCreateTimestamp')::BIGINT ="+ String.valueOf(date.getTime())));
		return criteria.list();
	}

	public void updateInteropXAllergyId(Integer id, String interopXAllergyId, DataProcessingStatusEnum processName, Boolean processStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update AllergyIntolerance set interopx_ai_id=:interopXAllergyId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processName.getName()+"}', to_jsonb("+processStatus+"), true)  where id=:id");
        qry.setParameter("id",id);
        qry.setParameter("interopXAllergyId", interopXAllergyId);
        qry.executeUpdate();
        session.clear();
	}
        
	@Override
	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus) {
		String sqlQuery = "update allergy_intolerance set interopx_patient_id=:interopXPatientId, data_processing_status=jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where data ->> 'patientId'= :actualPatientId";
		Query qry = getSession().createSQLQuery(sqlQuery);
        qry.setParameter("interopXPatientId", interopXPatientId);
        qry.setParameter("actualPatientId", actualPatientId);
		qry.executeUpdate();
	}

	@Override
	public List<Object> getUniqueAllergiesByInteropXPatientId(String interopXPatientId) {
		String hql = "from AllergyIntolerance where id IN (select min(id) from AllergyIntolerance where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}
}
