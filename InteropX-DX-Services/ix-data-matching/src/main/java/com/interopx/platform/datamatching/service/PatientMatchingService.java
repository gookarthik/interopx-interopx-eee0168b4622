package com.interopx.platform.datamatching.service;

import java.util.List;

import com.interopx.platform.repository.model.PatientResource;
import com.interopx.platform.repository.resource.Patient;

public interface PatientMatchingService {
	
	public List<PatientResource> processPatientMatchingAndLinking(Integer extractionId);
	
	public List<PatientResource> getPatientsBySearchCriteria(Patient criteriaPatient);
	
}
