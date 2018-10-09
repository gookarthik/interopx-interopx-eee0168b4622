package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.interopx.platform.repository.model.EncounterResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class EncounterDaoImpl extends AbstractDao implements EncounterDao {

	public EncounterResource saveOrUpdateEncounter(EncounterResource encounter) {
		getSession().saveOrUpdate(encounter);
		return encounter;
	}

	public List<EncounterResource> getAllEncounters() {
		List<EncounterResource> encounters = getSession().createCriteria(EncounterResource.class).list();
		return encounters;
	}

	public EncounterResource getEncounterById(Integer id) {
		EncounterResource encounter = (EncounterResource) getSession().get(EncounterResource.class, id);
		return encounter;
	}

	public List<EncounterResource> getEncounterResourcesByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		Criteria criteria = getSession().createCriteria(EncounterResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(patientId!=null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	@Override
	public List<EncounterResource> getEncountersByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		Criteria criteria = getSession().createCriteria(EncounterResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		return criteria.list();
	}

	@Override
	public List<EncounterResource> getDuplicateEncounters(Integer id, String code, Date date, String iXPatientId) {
		Criteria criteria = getSession().createCriteria(EncounterResource.class);
		criteria.add(Restrictions.ne("id", id));
		criteria.add(Restrictions.and(Restrictions.ne("interopxEncounterId",""), Restrictions.isNotNull("interopxEncounterId")));
		criteria.add(Restrictions.sqlRestriction("data->>'encounterCode' = '"+code+"'"));
		criteria.add(Restrictions.eq("interopxPatientId", iXPatientId));
		if(date!=null)
			criteria.add(Restrictions.sqlRestriction("(data->>'startTime')::BIGINT ="+ String.valueOf(date.getTime())));
		return criteria.list();
	}

	@Override
	public void updateInteropXEncounterId(Integer id, String interopXEncounterId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update EncounterResource set interopx_encounter_id=:interopXEncounterId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processName.getName()+"}', to_jsonb("+processStatus+"), true)  where id=:id");
        qry.setParameter("id",id);
        qry.setParameter("interopXEncounterId", interopXEncounterId);
        qry.executeUpdate();
        session.clear();
	}

    public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		String sqlQuery = "update encounter set interopx_patient_id=:interopXPatientId, data_processing_status=jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where data ->> 'patientId'= :actualPatientId";
		Query qry = getSession().createSQLQuery(sqlQuery);
        qry.setParameter("interopXPatientId", interopXPatientId);
        qry.setParameter("actualPatientId", actualPatientId);
		qry.executeUpdate();
		
	}

	@Override
	public List<Object> getUniqueEncountersByInteropXPatientId(String interopXPatientId) {
		String hql = "from EncounterResource where id IN (select min(id) from EncounterResource where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

}
