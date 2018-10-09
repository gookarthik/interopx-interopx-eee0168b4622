package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import com.interopx.platform.repository.model.DataProcessingStatus;
import com.interopx.platform.repository.model.ImmunizationResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface ImmunizationDao {

	public ImmunizationResource saveOrUpdateImmunization(ImmunizationResource immunizationResource);

	public List<ImmunizationResource> getAllImmunization();

	public ImmunizationResource getImmunizationById(Integer id);
	
	public List<ImmunizationResource> getImmunizationResourceByExtractionIdAndInternalPatientId(Integer etId,
			String patientId); 
	
	public List<ImmunizationResource> getImmunizationsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public List<ImmunizationResource> getDuplicateImmunizations(Integer id, String code, Date date, String iXPatientId);
	
	public void updateInteropXImmunizationId(Integer id, String interopXImmunizationId, DataProcessingStatusEnum processName, Boolean processStatus);

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus);
	
	public List<Object> getUniqueImmunizationsByInteropXPatientId(String interopXPatientId);

}
