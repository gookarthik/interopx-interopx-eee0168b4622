package com.interopx.platform.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.PatientDao;
import com.interopx.platform.repository.model.PatientResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientDao patientDao;

	public PatientResource saveOrUpdatePatient(PatientResource patient) {
		return patientDao.saveOrUpdatePatient(patient);
	}

	public List<PatientResource> getAllPatients() {
		return patientDao.getAllPatients();
	}

	public PatientResource getPatientById(Integer id) {
		return patientDao.getPatientById(id);
	}

	public List<PatientResource> getPatientsByetIdandProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus) {
		return patientDao.getPatientsByetIdandProcessingStatus(etId, statusEnum, processStatus);
	}

	public void updateInteropXPatientId(Integer id, String interopXPatientId, DataProcessingStatusEnum processingName, boolean processingStatus) {
		patientDao.updateInteropXPatientId(id, interopXPatientId, processingName, processingStatus);
	}

	@Override
	public List<PatientResource> getPatientByInteropXId(String interopXId) {
		return patientDao.getPatientByInteropXId(interopXId);
	}

	@Override
	public List<PatientResource> getUniquePatients() {
		return patientDao.getUniquePatients();
	}
	
	public List<PatientResource> getPatientResourcesByExtractionIdAndInternalPatientId(Integer etId){
		return patientDao.getPatientResourcesByExtractionIdAndInternalPatientId(etId);

	}

	@Override
	public List<Object> getUniquePatientByInteropXPatientId(String interopXPatientId) {
		return patientDao.getUniquePatientByInteropXPatientId(interopXPatientId);
	}

	@Override
	public List<PatientResource> getPatientsByEtIdAndInternalPatientIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum processingName, boolean processingStatus) {
		return patientDao.getPatientsByEtIdAndInternalPatientIdAndProcessingStatus(etId, processingName, processingStatus);
	}

}
