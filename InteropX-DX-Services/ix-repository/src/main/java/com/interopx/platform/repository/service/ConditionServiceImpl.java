package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.ConditionDao;
import com.interopx.platform.repository.model.ConditionResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class ConditionServiceImpl implements ConditionService {

	@Autowired
	private ConditionDao conditionDao;

	public ConditionResource saveOrUpdateCondition(ConditionResource condition) {
		return conditionDao.saveOrUpdateCondition(condition);
	}

	public List<ConditionResource> getAllConditions() {
		return conditionDao.getAllConditions();
	}

	public ConditionResource getConditionById(Integer id) {
		return conditionDao.getConditionById(id);
	}
	public List<ConditionResource> getConditionResourceByExtractionIdAndInternalPatientId(Integer etId, String patientId){
		return conditionDao.getConditionResourcesByExtractionIdAndInternalPatientId(etId, patientId);

	}

	@Override
	public List<ConditionResource> getConditionsByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		return conditionDao.getConditionsByEtIdAndProcessingStatus(etId, statusEnum, processStatus);
	}

	@Override
	public List<ConditionResource> getDuplicateConditions(Integer id, String code, Date date, String iXPatientId) {
		return conditionDao.getDuplicateConditions(id, code, date, iXPatientId);
	}

	@Override
	public void updateInteropXConditionId(Integer id, String interopXConditionId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		conditionDao.updateInteropXConditionId(id, interopXConditionId, processName, processStatus);
	}
	
	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		conditionDao.updateInteropXPatientId(interopXPatientId, actualPatientId,processingName, processingStatus);
	}

	@Override
	public List<Object> getUniqueConditionsByInteropXPatientId(String interopXPatientId) {
		return conditionDao.getUniqueConditionsByInteropXPatientId(interopXPatientId);
	}
}
