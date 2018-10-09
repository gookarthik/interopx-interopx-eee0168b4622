package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.ConditionResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class ConditionDaoImpl extends AbstractDao implements ConditionDao {

	public ConditionResource saveOrUpdateCondition(ConditionResource condition) {
		getSession().saveOrUpdate(condition);
		return condition;
	}

	public List<ConditionResource> getAllConditions() {
		List<ConditionResource> conditions = getSession().createCriteria(ConditionResource.class).list();
		return conditions;
	}

	public ConditionResource getConditionById(Integer id) {
		ConditionResource condition = (ConditionResource) getSession().get(ConditionResource.class, id);
		return condition;
	}

	
	public List<ConditionResource> getConditionResourcesByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		Criteria criteria = getSession().createCriteria(ConditionResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(patientId!=null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	@Override
	public List<ConditionResource> getConditionsByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		Criteria criteria = getSession().createCriteria(ConditionResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		return criteria.list();
	}

	@Override
	public List<ConditionResource> getDuplicateConditions(Integer id, String code, Date date, String iXPatientId) {
		Criteria criteria = getSession().createCriteria(ConditionResource.class);
		criteria.add(Restrictions.ne("id", id));
		criteria.add(Restrictions.and(Restrictions.ne("interopxConditionId",""), Restrictions.isNotNull("interopxConditionId")));
		criteria.add(Restrictions.sqlRestriction("data->>'problemCode' = '"+code+"'"));
		criteria.add(Restrictions.eq("interopxPatientId", iXPatientId));
		if(date!=null)
			criteria.add(Restrictions.sqlRestriction("(data->>'createTimestamp')::BIGINT ="+ String.valueOf(date.getTime())));
		return criteria.list();
	}

	@Override
	public void updateInteropXConditionId(Integer id, String interopXConditionId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update ConditionResource set interopx_condition_id=:interopXConditionId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processName.getName()+"}', to_jsonb("+processStatus+"), true)  where id=:id");
        qry.setParameter("id",id);
        qry.setParameter("interopXConditionId", interopXConditionId);
        qry.executeUpdate();
        session.clear();
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		String sqlQuery = "update condition set interopx_patient_id=:interopXPatientId, data_processing_status=jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where data ->> 'patientId'= :actualPatientId";
		Query qry = getSession().createSQLQuery(sqlQuery);
        qry.setParameter("interopXPatientId", interopXPatientId);
        qry.setParameter("actualPatientId", actualPatientId);
		qry.executeUpdate();
		
	}

	@Override
	public List<Object> getUniqueConditionsByInteropXPatientId(String interopXPatientId) {
		String hql = "from ConditionResource where id IN (select min(id) from ConditionResource where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

}
