package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.ImmunizationDao;
import com.interopx.platform.repository.model.ImmunizationResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;


@Service
@Transactional
public class ImmunizationServiceImpl implements ImmunizationService {

	@Autowired
	private ImmunizationDao immunizationDao;

	public ImmunizationResource saveOrUpdateImmunization(ImmunizationResource immunization) {
		return immunizationDao.saveOrUpdateImmunization(immunization);
	}

	public List<ImmunizationResource> getAllImmunization() {
		return immunizationDao.getAllImmunization();
	}

	public ImmunizationResource getImmunizationById(Integer id) {
		return immunizationDao.getImmunizationById(id);
	}
	public List<ImmunizationResource> getImmunizationResourceByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		return immunizationDao.getImmunizationResourceByExtractionIdAndInternalPatientId(etId, patientId);

	}

	@Override
	public List<ImmunizationResource> getImmunizationsByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		return immunizationDao.getImmunizationsByEtIdAndProcessingStatus(etId, statusEnum, processStatus);
	}

	@Override
	public List<ImmunizationResource> getDuplicateImmunizations(Integer id, String code, Date date,
			String iXPatientId) {
		return immunizationDao.getDuplicateImmunizations(id, code, date, iXPatientId);
	}

	@Override
	public void updateInteropXImmunizationId(Integer id, String interopXImmunizationId,
			DataProcessingStatusEnum processName, Boolean processStatus) {
		immunizationDao.updateInteropXImmunizationId(id, interopXImmunizationId, processName, processStatus);
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		immunizationDao.updateInteropXPatientId(interopXPatientId, actualPatientId,processingName, processingStatus);
	}

	@Override
	public List<Object> getUniqueImmunizationsByInteropXPatientId(String interopXPatientId) {
		return immunizationDao.getUniqueImmunizationsByInteropXPatientId(interopXPatientId);
	}

}
