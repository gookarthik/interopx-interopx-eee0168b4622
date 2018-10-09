package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import com.interopx.platform.repository.model.ConditionResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface ConditionService {

	public ConditionResource saveOrUpdateCondition(ConditionResource condition);

	public List<ConditionResource> getAllConditions();

	public ConditionResource getConditionById(Integer id);
	
	public List<ConditionResource> getConditionResourceByExtractionIdAndInternalPatientId(Integer etId, String patientId);
	
	public List<ConditionResource> getConditionsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public List<ConditionResource> getDuplicateConditions(Integer id, String code, Date date, String iXPatientId);
	
	public void updateInteropXConditionId(Integer id, String interopXConditionId, DataProcessingStatusEnum processName, Boolean processStatus);

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus);

	public List<Object> getUniqueConditionsByInteropXPatientId(String interopXPatientId);

}
