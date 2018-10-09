package com.interopx.platform.repository.dao;

import java.util.Date;
import java.util.List;

import com.interopx.platform.repository.model.AllergyIntolerance;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;


public interface AllergyIntoleranceDao {
	
	public AllergyIntolerance saveOrUpdateAllergy(AllergyIntolerance patient);
	
	public List<AllergyIntolerance> getAllAllergyIntolerance();
	
	public AllergyIntolerance getAllergyIntoleranceById(Integer id);
	
	public List<AllergyIntolerance> getAllergyResourceByExtractionIdAndInternalPatientId(Integer etId, String patientId);
	
	public List<AllergyIntolerance> getAllergiesByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public List<AllergyIntolerance> getDuplicateAllergies(Integer id, String code, Date date, String iXPatientId);
	
	public void updateInteropXAllergyId(Integer id, String interopXAllergyId, DataProcessingStatusEnum processName, Boolean processStatus);

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean proccesingStatus);
	
	public List<Object> getUniqueAllergiesByInteropXPatientId(String interopXPatientId);

}
