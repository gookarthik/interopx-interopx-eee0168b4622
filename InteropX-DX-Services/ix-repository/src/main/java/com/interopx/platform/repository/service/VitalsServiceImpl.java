package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.VitalsDao;
import com.interopx.platform.repository.model.VitalSigns;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class VitalsServiceImpl implements VitalsService {

	@Autowired
	private VitalsDao vitalsDao;

	public VitalSigns saveOrUpdateVital(VitalSigns vitals) {
		return vitalsDao.saveOrUpdateVital(vitals);
	}

	public List<VitalSigns> getAllVitals() {
		return vitalsDao.getAllVitals();
	}

	public VitalSigns getVitalById(Integer id) {
		return vitalsDao.getVitalById(id);
	}
	public List<VitalSigns> getVitalSignsByExtractionIdAndInternalPatientId(Integer etId, String patientId){
		return vitalsDao.getVitalSignsByExtractionIdAndInternalPatientId(etId, patientId);

	}

	@Override
	public List<VitalSigns> getVitalsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus) {
		return vitalsDao.getVitalsByEtIdAndProcessingStatus(etId, statusEnum, processStatus);
	}

	@Override
	public void updateInteropXVitalId(Integer id, String interopXVitalId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		vitalsDao.updateInteropXVitalId(id, interopXVitalId, processName, processStatus);
	}
		
	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		vitalsDao.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
	}

	public List<VitalSigns> getDuplicateVitalSigns(Integer id, String code, Date date, String iXPatientId) {
		return vitalsDao.getDuplicateVitalSigns(id, code, date, iXPatientId);
	}

	@Override
	public List<Object> getUniqueVitalSignsByInteropXPatientId(String interopXPatientId) {
		return vitalsDao.getUniqueVitalSignsByInteropXPatientId(interopXPatientId);
	}

}
