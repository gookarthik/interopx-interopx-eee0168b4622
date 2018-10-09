package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import com.interopx.platform.repository.model.MedicationResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface MedicationService {

	public MedicationResource saveOrUpdateMedication(MedicationResource medication);

	public List<MedicationResource> getAllMedication();

	public MedicationResource getMedicationById(Integer id);
	
	public List<MedicationResource> getMedicationStatementsByExtractionIdAndInternalPatientId(Integer etId,
			String patientId);
	
	public List<MedicationResource> getMedicationsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public List<MedicationResource> getDuplicateMedicaitons(Integer id, String code, Date date, String iXPatientId);
	
	public void updateInteropXMedicationId(Integer id, String interopXMedicationId, DataProcessingStatusEnum processName, Boolean processStatus);

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus);

	public List<Object> getUniqueMedicationsByInteropXPatientId(String interopXPatientId);
}
