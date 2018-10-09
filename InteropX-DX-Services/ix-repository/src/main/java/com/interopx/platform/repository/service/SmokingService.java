package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import com.interopx.platform.repository.model.SmokingResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface SmokingService {

	public SmokingResource saveOrUpdateSmoke(SmokingResource smoke);

	public List<SmokingResource> getAllSmoking();

	public SmokingResource getSmokeById(Integer id);
	
	public List<SmokingResource> getSmokingResourceByExtractionIdAndInternalPatientId(Integer etId, String patientId);
	
	public List<SmokingResource> getSmokingsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public void updateInteropXSmokingId(Integer id, String interopXSmokingId, DataProcessingStatusEnum processName, Boolean processStatus);

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus);

	public List<SmokingResource> getDuplicateSmokings(Integer id, String smokingStatusCode, Date startTime,
			String interopxPatientId);

	public List<Object> getUniqueSmokingsByInteropXPatientId(String interopXPatientId);

}
