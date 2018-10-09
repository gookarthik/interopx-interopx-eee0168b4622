package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import com.interopx.platform.repository.model.ProcedureResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface ProcedureService {

	public ProcedureResource saveOrUpdateProcedure(ProcedureResource procedure);

	public List<ProcedureResource> getAllProcedure();

	public ProcedureResource getProcedureById(Integer id);
	
	public List<ProcedureResource> getProcedureByExtractionIdAndInternalPatientId(Integer etId, String patientId);
	
	public List<ProcedureResource> getProceduresByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public List<ProcedureResource> getDuplicateProcedures(Integer id, String code, Date date, String iXPatientId);
	
	public void updateInteropXProcedureId(Integer id, String interopXProcedureId, DataProcessingStatusEnum processName, Boolean processStatus);

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus);

	public List<Object> getUniqueProceduresByInteropXPatientId(String interopXPatientId);

}
