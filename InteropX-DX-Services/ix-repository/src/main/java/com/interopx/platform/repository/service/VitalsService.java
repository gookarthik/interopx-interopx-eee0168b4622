package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import com.interopx.platform.repository.model.VitalSigns;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface VitalsService {
	
	public VitalSigns saveOrUpdateVital(VitalSigns vitals);

	public List<VitalSigns> getAllVitals();

	public VitalSigns getVitalById(Integer id);
	
	public List<VitalSigns> getVitalSignsByExtractionIdAndInternalPatientId(Integer etId, String patientId);
	
	public List<VitalSigns> getVitalsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public void updateInteropXVitalId(Integer id, String interopXVitalId, DataProcessingStatusEnum processName, Boolean processStatus);

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus);
	
	public List<VitalSigns> getDuplicateVitalSigns(Integer id, String code, Date date, String iXPatientId);

	public List<Object> getUniqueVitalSignsByInteropXPatientId(String interopXPatientId);

}
