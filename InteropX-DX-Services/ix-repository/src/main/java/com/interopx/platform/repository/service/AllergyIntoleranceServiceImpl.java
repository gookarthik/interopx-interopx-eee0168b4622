package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.AllergyIntoleranceDao;
import com.interopx.platform.repository.model.AllergyIntolerance;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class AllergyIntoleranceServiceImpl implements AllergyIntoleranceService {

	@Autowired
	private AllergyIntoleranceDao allergyIntoleranceDao;
	
	public AllergyIntolerance saveOrUpdateAllergy(AllergyIntolerance allergy) {
		return allergyIntoleranceDao.saveOrUpdateAllergy(allergy);
	}

	public List<AllergyIntolerance> getAllAllergyIntolerance() {
		return allergyIntoleranceDao.getAllAllergyIntolerance();
	}

	public AllergyIntolerance getAllergyIntoleranceById(Integer id) {
		return allergyIntoleranceDao.getAllergyIntoleranceById(id);
	}
	public List<AllergyIntolerance> getAllergyResourceByExtractionIdAndInternalPatientId(Integer etId, String patientId){
		return allergyIntoleranceDao.getAllergyResourceByExtractionIdAndInternalPatientId(etId, patientId);

	}

	public List<AllergyIntolerance> getAllergiesByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		return allergyIntoleranceDao.getAllergiesByEtIdAndProcessingStatus(etId, statusEnum, processStatus);
	}

	@Override
	public List<AllergyIntolerance> getDuplicateAllergies(Integer id, String code, Date date, String iXPatientId) {
		return allergyIntoleranceDao.getDuplicateAllergies(id, code, date, iXPatientId);
	}

	@Override
	public void updateInteropXAllergyId(Integer id, String interopXAllergyId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		allergyIntoleranceDao.updateInteropXAllergyId(id, interopXAllergyId, processName, processStatus);
	}
	
	@Override
	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		allergyIntoleranceDao.updateInteropXPatientId(interopXPatientId, actualPatientId,processingName, processingStatus);
		
	}

	@Override
	public List<Object> getUniqueAllergiesByInteropXPatientId(String interopXPatientId) {
		return allergyIntoleranceDao.getUniqueAllergiesByInteropXPatientId(interopXPatientId);
	}
}
