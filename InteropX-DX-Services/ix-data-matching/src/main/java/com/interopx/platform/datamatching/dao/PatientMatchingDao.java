package com.interopx.platform.datamatching.dao;

import java.util.List;

import com.interopx.platform.repository.model.PatientResource;
import com.interopx.platform.repository.resource.Patient;

public interface PatientMatchingDao {
	
	public List<PatientResource> getPatientsBySearchCriteria(Patient patient);
	
}
