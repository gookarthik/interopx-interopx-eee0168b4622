package com.interopx.platform.repository.service;

import java.util.List;

import com.interopx.platform.repository.model.PatientResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;


public interface PatientService {

	public PatientResource saveOrUpdatePatient(PatientResource patient);

	public List<PatientResource> getAllPatients();

	public PatientResource getPatientById(Integer id);
	
	public List<PatientResource> getPatientsByetIdandProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum, boolean processStatus);
	
	public void updateInteropXPatientId(Integer id, String interopXPatientId, DataProcessingStatusEnum processingName, boolean processingStatus);
	
	public List<PatientResource> getPatientByInteropXId(String interopXId);
	
	public List<PatientResource> getUniquePatients();
	
	public List<PatientResource> getPatientResourcesByExtractionIdAndInternalPatientId(Integer etId);

	public List<Object> getUniquePatientByInteropXPatientId(String interopXPatientId);
	
	public List<PatientResource> getPatientsByEtIdAndInternalPatientIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum processingName,boolean processingStatus);

}
