package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;
import com.interopx.platform.repository.model.LabResultsResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface LabResultsService {

	public LabResultsResource saveOrUpdateLabResults(LabResultsResource labResults);

	public List<LabResultsResource> getAllLabResults();

	public LabResultsResource getLabResultsById(Integer id);
	
	public List<LabResultsResource> getLabResultsResourceByExtractionIdAndInternalPatientId(Integer etId,String patientId);
	
	public List<LabResultsResource> getLabResultByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public void updateInteropXLabResultId(Integer id, String interopXLabId, DataProcessingStatusEnum processName, Boolean processStatus);

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus);
	
	public List<LabResultsResource> getDuplicateLabResults(Integer id, String originalName, String labType, Date date, String iXPatientId);

	public List<Object> getUniqueLabResultsByInteropXPatientId(String interopXPatientId);

}
