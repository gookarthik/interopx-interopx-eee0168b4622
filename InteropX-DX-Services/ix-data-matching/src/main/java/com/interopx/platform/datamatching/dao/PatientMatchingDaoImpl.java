package com.interopx.platform.datamatching.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.dao.AbstractDao;
import com.interopx.platform.repository.model.PatientResource;
import com.interopx.platform.repository.resource.Patient;

@Repository
public class PatientMatchingDaoImpl extends AbstractDao implements PatientMatchingDao {

	public List<PatientResource> getPatientsBySearchCriteria(Patient patient) {
		
		Criteria criteria = getSession().createCriteria(PatientResource.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Disjunction disjunction = Restrictions.disjunction();
		
		if (patient.getPatientId() != null && !patient.getPatientId().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'patientId' = '" + patient.getPatientId() + "'"));
		}
		if (patient.getCity() != null && !patient.getCity().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'city' = '" + patient.getCity() + "'"));
		}
		if (patient.getEncId() != null && !patient.getEncId().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'encId' = '" + patient.getEncId() + "'"));
		}
		if (patient.getEthnicityCode() != null && !patient.getEthnicityCode().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'ethnicityCode' = '" + patient.getEthnicityCode() + "'"));
		}
		if (patient.getAliveIndicator() != null && !patient.getAliveIndicator().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'aliveIndicator' = '" + patient.getAliveIndicator() + "'"));
		}
		if (patient.getFirstName() != null && !patient.getFirstName().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'firstName' ilike '%" + patient.getFirstName() + "%'"));
		}
		if (patient.getLastName() != null && !patient.getLastName().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'lastName' ilike '%" + patient.getLastName() + "%'"));
		}
		if (patient.getMiddleInitial() != null && !patient.getMiddleInitial().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'middleInitial' ilike '%" + patient.getMiddleInitial() + "%'"));
		}
		if (patient.getHAOID() != null && !patient.getHAOID().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'haoid' = '" + patient.getHAOID() + "'"));
		}
		if (patient.getLanguage() != null && !patient.getLanguage().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'language' = '" + patient.getLanguage() + "'"));
		}
		if (patient.getTelecom() != null && !patient.getTelecom().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'telecom' = '" + patient.getTelecom() + "'"));
		}
		if (patient.getPatIdExtension() != null && !patient.getPatIdExtension().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'patIdExtension' = '" + patient.getPatIdExtension() + "'"));
		}
		if (patient.getRaceCode() != null && !patient.getRaceCode().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'raceCode' = '" + patient.getRaceCode() + "'"));
		}
		if (patient.getSiteOID() != null && !patient.getSiteOID().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'siteOID' = '" + patient.getSiteOID() + "'"));
		}
		if (patient.getState() != null && !patient.getState().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'state' = '" + patient.getState() + "'"));
		}
		if (patient.getStreetAddress() != null && !patient.getStreetAddress().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'streetAddress' = '" + patient.getStreetAddress() + "'"));
		}
		if (patient.getZip() != null && !patient.getZip().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'zip' = '" + patient.getZip() + "'"));
		}
		if (patient.getDeceasedDateTime() != null	&& !patient.getDeceasedDateTime().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'deceasedDateTime' = '" + patient.getDeceasedDateTime() + "'"));
		}
		if (patient.getDateOfBirth() != null	&& !patient.getDateOfBirth().isEmpty()) {
			disjunction.add(Restrictions.sqlRestriction("{alias}.data->>'dateOfBirth' = '" + patient.getDateOfBirth() + "'"));
		}
		
		//where adminGender=x and(all or conditions)
		if (patient.getAdminGender() != null && !patient.getAdminGender().isEmpty()) {
			criteria.add(Restrictions.sqlRestriction("{alias}.data->>'adminGender' = '" + patient.getAdminGender() + "'"));
		}
		criteria.add(disjunction);
		criteria.add(Restrictions.and(Restrictions.isNotNull("interopxPatientId"), Restrictions.ne("interopxPatientId", "")));
		
		return criteria.list();
	}

}
