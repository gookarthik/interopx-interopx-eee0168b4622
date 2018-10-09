package com.interopx.platform.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.PatientResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class PatientDaoImpl extends AbstractDao implements PatientDao {

	public PatientResource saveOrUpdatePatient(PatientResource patientResource) {
		getSession().saveOrUpdate(patientResource);
		return patientResource;
	}

	public List<PatientResource> getAllPatients() {
		List<PatientResource> patients = getSession().createCriteria(PatientResource.class).list();

		return patients;
	}

	public PatientResource getPatientById(Integer id) {
		PatientResource patient = (PatientResource) getSession().get(PatientResource.class, id);
		return patient;
	}

	public List<PatientResource> getPatientsByetIdandProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus) {
		Criteria criteria = getSession().createCriteria(PatientResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(statusEnum != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+statusEnum.getName()+"')::boolean is "+processStatus));
		List<PatientResource> patients = criteria.list();
		return patients;
	}

	public void updateInteropXPatientId(Integer id, String interopXPatientId, DataProcessingStatusEnum processingName, boolean processingStatus) {
		Session session = getSession();
		Query qry = session.createQuery("update PatientResource set interopx_patient_id=:interopXPatientId, data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processingName.getName()+"}', to_jsonb("+processingStatus+"),true)  where id=:id");
        qry.setParameter("id",id);
        qry.setParameter("interopXPatientId", interopXPatientId);
		Integer res = qry.executeUpdate();
		session.clear();
	}

	@Override
	public List<PatientResource> getPatientByInteropXId(String interopXId) {
		Criteria criteria = getSession().createCriteria(PatientResource.class);
		criteria.add(Restrictions.eq("interopxPatientId", interopXId));
		return criteria.list();
	}

	@Override
	public List<PatientResource> getUniquePatients() {
		SQLQuery query = getSession().createSQLQuery(
				"select distinct on (interopx_patient_id) interopx_patient_id, actual_patient_id,extraction_id,ds_id,id,first_name,last_name,dob,gender,ds_name,(data#>>'{}')\\:\\:text as data from patient where interopx_patient_id IS NOT NULL");
		List<Object[]> rows = query.list();
		List<PatientResource> pList = new ArrayList<PatientResource>();
		for (Object[] row : rows) {
			PatientResource p = new PatientResource();

			if(row[0]!=null)
				p.setInteropxPatientId(row[0].toString());
				
				if(row[1]!=null)
				p.setActualPatientId(row[1].toString());

				if(row[2]!=null)
					p.setExtractionTaskId(Integer.parseInt(row[2].toString()));

				if(row[3]!=null)
					p.setDataSourceId(Integer.parseInt(row[3].toString()));
				
				if(row[4]!=null)
					p.setId(Integer.parseInt(row[4].toString()));
				
				if(row[5]!=null)
				p.setFirstName(row[5].toString());
				
				if(row[6]!=null)
				p.setLastName(row[6].toString());
				
				if(row[7]!=null)
					p.setDob(row[7].toString());
				
				if(row[8]!=null)
					p.setGender(row[8].toString());
				
				if(row[9]!=null)
					p.setDataSourceName(row[9].toString());
				
				if(row[10]!=null)
					p.setData(row[10].toString());
				
			pList.add(p);
		}

		return pList;
	}

	public List<PatientResource> getPatientResourcesByExtractionIdAndInternalPatientId(Integer etId) {
		Criteria criteria = getSession().createCriteria(PatientResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		return criteria.list();
	}

	@Override
	public List<Object> getUniquePatientByInteropXPatientId(String interopXPatientId) {
		String hql = "from PatientResource where id IN (select min(id) from PatientResource where interopxPatientId='"+interopXPatientId+"' group by interopxPatientId )";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

	@Override
	public List<PatientResource> getPatientsByEtIdAndInternalPatientIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum processingName, boolean processingStatus) {
		Criteria criteria = getSession().createCriteria(PatientResource.class);
		criteria.add(Restrictions.eq("extractionTaskId", etId));
		if(processingName != null)
			criteria.add(Restrictions
				.sqlRestriction("(data_processing_status->>'"+processingName.getName()+"')::boolean is "+processingStatus));
		criteria.add(Restrictions.ne("interopxPatientId", ""));
		List<PatientResource> patients = criteria.list();
		return patients;
	}
}
