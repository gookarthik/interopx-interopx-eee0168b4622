package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import com.interopx.platform.repository.model.EncounterResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;


public interface EncounterService {

	public EncounterResource saveOrUpdateEncounter(EncounterResource encounter);

	public List<EncounterResource> getAllEncounters();

	public EncounterResource getEncounterById(Integer id);
	
	public  List<EncounterResource> getEncounterResourcesByExtractionIdAndInternalPatientId(Integer etId, String patientId);
	
	public List<EncounterResource> getEncountersByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public List<EncounterResource> getDuplicateEncounters(Integer id, String code, Date date, String iXPatientId);
	
	public void updateInteropXEncounterId(Integer id, String interopXEncounterId, DataProcessingStatusEnum processName, Boolean processStatus);

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus);

	public List<Object> getUniqueEncountersByInteropXPatientId(String interopXPatientId);

}
