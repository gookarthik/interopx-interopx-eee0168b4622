package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.AllergyIntolerance;
import com.interopx.platform.repository.model.LabResultsResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class LabResultsDaoImpl extends AbstractDao implements LabResultsDao {

	public LabResultsResource saveOrUpdateLabResults(LabResultsResource labResults) {
		getSession().saveOrUpdate(labResults);
		return labResults;
	}

	public List<LabResultsResource> getAllLabResults() {
		List<LabResultsResource> labResults = getSession().createCriteria(LabResultsResource.class).list();
		return labResults;
	}

	public LabResultsResource getLabResultsById(Integer id) {
		LabResultsResource labResult = (LabResultsResource) getSession().get(LabResultsResource.class, id);
		return labResult;
	}

	public List<LabResultsResource> getLabResultsResourceByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		Criteria criteria = getSession().createCriteria(LabResultsResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(patientId!=null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	@Override
	public List<LabResultsResource> getLabResultByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		Criteria criteria = getSession().createCriteria(LabResultsResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		return criteria.list();
	}

	@Override
	public void updateInteropXLabResultId(Integer id, String interopXLabId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update LabResultsResource set interopx_lab_id=:interopxLabId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processName.getName()+"}', to_jsonb("+processStatus+"), true)  where id=:id");
        qry.setParameter("id",id);
        qry.setParameter("interopxLabId", interopXLabId);
        qry.executeUpdate();
        session.clear();
	}
	
	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		String sqlQuery = "update lab_results set interopx_patient_id=:interopXPatientId, data_processing_status=jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where data ->> 'patientId'= :actualPatientId";
		Query qry = getSession().createSQLQuery(sqlQuery);
        qry.setParameter("interopXPatientId", interopXPatientId);
        qry.setParameter("actualPatientId", actualPatientId);
		qry.executeUpdate();
		
	}

	@Override
	public List<LabResultsResource> getDuplicateLabResults(Integer id, String originalName, String labType, Date date, String iXPatientId) {
		Criteria criteria = getSession().createCriteria(LabResultsResource.class);
		criteria.add(Restrictions.ne("id", id));
		criteria.add(Restrictions.and(Restrictions.ne("interopxLabId",""), Restrictions.isNotNull("interopxLabId")));
		criteria.add(Restrictions.sqlRestriction("data->>'originalName' LIKE '"+originalName+"'"));
		criteria.add(Restrictions.sqlRestriction("data->>'labType' LIKE '"+labType+"'"));
		criteria.add(Restrictions.eq("interopxPatientId", iXPatientId));
		if(date!=null)
			criteria.add(Restrictions.sqlRestriction("(data->>'collectionDate')::BIGINT ="+ String.valueOf(date.getTime())));
		return criteria.list();
	}

	@Override
	public List<Object> getUniqueLabResultsByInteropXPatientId(String interopXPatientId) {
		String hql = "from LabResultsResource where id IN (select min(id) from LabResultsResource where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

}
