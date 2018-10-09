package com.interopx.platform.repository.dao;

import java.util.List;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.LocationResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class LocationDaoImpl extends AbstractDao implements LocationDao {

	public LocationResource saveOrUpdateLocation(LocationResource location) {
		getSession().saveOrUpdate(location);
		return location;
	}

	public List<LocationResource> getAllLocations() {
		List<LocationResource> locations = getSession().createCriteria(LocationResource.class).list();
		return locations;
	}

	public LocationResource getLocationById(Integer id) {
		LocationResource location = (LocationResource) getSession().get(LocationResource.class, id);
		return location;
	}

	public List<LocationResource> getLocationResourceByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		Criteria criteria = getSession().createCriteria(LocationResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if (patientId != null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	@Override
	public List<LocationResource> getLocationsByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		Criteria criteria = getSession().createCriteria(LocationResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		return criteria.list();
	}

	@Override
	public List<LocationResource> getDuplicateLocations(Integer id, String locaType, String locationCity,
			String locationState, String phone, String iXPatientId) {
		Criteria criteria = getSession().createCriteria(LocationResource.class);
		criteria.add(Restrictions.ne("id", id));
		criteria.add(Restrictions.and(Restrictions.ne("interopxLocationId",""), Restrictions.isNotNull("interopxLocationId")));
		criteria.add(Restrictions.sqlRestriction("data->>'locType' = '"+locaType+"'"));
		criteria.add(Restrictions.sqlRestriction("data->>'locationCity' = '"+locationCity+"'"));
		criteria.add(Restrictions.sqlRestriction("data->>'locationState' = '"+locationState+"'"));
		criteria.add(Restrictions.sqlRestriction("data->>'phone' = '"+phone+"'"));
		criteria.add(Restrictions.eq("interopxPatientId", iXPatientId));
		return criteria.list();
	}

	@Override
	public void updateInteropXLocationId(Integer id, String interopXLocationId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update LocationResource set interopx_location_id=:interopXLocationId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"
						+ processName.getName() + "}', to_jsonb(" + processStatus + "), true)  where id=:id");
		qry.setParameter("id", id);
		qry.setParameter("interopXLocationId", interopXLocationId);
		qry.executeUpdate();
		session.clear();
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		String sqlQuery = "update location set interopx_patient_id=:interopXPatientId, data_processing_status=jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where data ->> 'patientId'= :actualPatientId";
		Query qry = getSession().createSQLQuery(sqlQuery);
        qry.setParameter("interopXPatientId", interopXPatientId);
        qry.setParameter("actualPatientId", actualPatientId);
		qry.executeUpdate();
		
	}

	@Override
	public List<Object> getUniqueLocationsByInteropXPatientId(String interopXPatientId) {
		String hql = "from LocationResource where id IN (select min(id) from LocationResource where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

}
