package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.SmokingDao;
import com.interopx.platform.repository.model.SmokingResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class SmokingServiceImpl implements SmokingService {

	@Autowired
	private SmokingDao smokingDao;

	public SmokingResource saveOrUpdateSmoke(SmokingResource smoke) {
		return smokingDao.saveOrUpdateSmoke(smoke);
	}

	public List<SmokingResource> getAllSmoking() {
		return smokingDao.getAllSmoking();
	}

	public SmokingResource getSmokeById(Integer id) {
		return smokingDao.getSmokeById(id);
	}
	
	public List<SmokingResource> getSmokingResourceByExtractionIdAndInternalPatientId(Integer etId, String patientId){
		return smokingDao.getSmokingResourceByExtractionIdAndInternalPatientId(etId, patientId);
	}

	@Override
	public List<SmokingResource> getSmokingsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus) {
		return smokingDao.getSmokingsByEtIdAndProcessingStatus(etId, statusEnum, processStatus);
	}

	@Override
	public void updateInteropXSmokingId(Integer id, String interopXSmokingId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		smokingDao.updateInteropXSmokingId(id, interopXSmokingId, processName, processStatus);
	}
	
	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		smokingDao.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
	}

	@Override
	public List<SmokingResource> getDuplicateSmokings(Integer id, String smokingStatusCode, Date startTime,
			String interopxPatientId) {
		return smokingDao.getDuplicateSmokings(id, smokingStatusCode, startTime, interopxPatientId);
	}

	@Override
	public List<Object> getUniqueSmokingsByInteropXPatientId(String interopXPatientId) {
		return smokingDao.getUniqueSmokingsByInteropXPatientId(interopXPatientId);
	}

	
}
