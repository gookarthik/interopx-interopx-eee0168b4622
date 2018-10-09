package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.LabResultsDao;
import com.interopx.platform.repository.model.LabResultsResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class LabResultsServiceImpl implements LabResultsService {

	@Autowired
	private LabResultsDao labResultsDao;

	public LabResultsResource saveOrUpdateLabResults(LabResultsResource labResults) {
		return labResultsDao.saveOrUpdateLabResults(labResults);
	}

	public List<LabResultsResource> getAllLabResults() {
		return labResultsDao.getAllLabResults();
	}

	public LabResultsResource getLabResultsById(Integer id) {
		return labResultsDao.getLabResultsById(id);
	}
	public List<LabResultsResource> getLabResultsResourceByExtractionIdAndInternalPatientId(Integer etId,String patientId){
		return labResultsDao.getLabResultsResourceByExtractionIdAndInternalPatientId(etId, patientId);
	}

	@Override
	public List<LabResultsResource> getLabResultByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		return labResultsDao.getLabResultByEtIdAndProcessingStatus(etId, statusEnum, processStatus);
	}

	@Override
	public void updateInteropXLabResultId(Integer id, String interopXLabId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		labResultsDao.updateInteropXLabResultId(id, interopXLabId, processName, processStatus);
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		labResultsDao.updateInteropXPatientId(interopXPatientId, actualPatientId,processingName, processingStatus);
	}

	public List<LabResultsResource> getDuplicateLabResults(Integer id, String originalName, String labType, Date date, String iXPatientId) {
		return labResultsDao.getDuplicateLabResults(id, originalName, labType, date, iXPatientId);
	}

	@Override
	public List<Object> getUniqueLabResultsByInteropXPatientId(String interopXPatientId) {
		return labResultsDao.getUniqueLabResultsByInteropXPatientId(interopXPatientId);
	}

}
