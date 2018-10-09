package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.EncounterDao;
import com.interopx.platform.repository.model.EncounterResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class EncounterServiceImpl implements EncounterService {

	@Autowired
	private EncounterDao encounterDao;

	public EncounterResource saveOrUpdateEncounter(EncounterResource encounter) {
		return encounterDao.saveOrUpdateEncounter(encounter);
	}

	public List<EncounterResource> getAllEncounters() {
		return encounterDao.getAllEncounters();
	}

	public EncounterResource getEncounterById(Integer id) {
		return encounterDao.getEncounterById(id);
	}
	public List<EncounterResource> getEncounterResourcesByExtractionIdAndInternalPatientId(Integer etId, String patientId){
		return encounterDao.getEncounterResourcesByExtractionIdAndInternalPatientId(etId, patientId);
		}

	@Override
	public List<EncounterResource> getEncountersByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		return encounterDao.getEncountersByEtIdAndProcessingStatus(etId, statusEnum, processStatus);
	}

	@Override
	public List<EncounterResource> getDuplicateEncounters(Integer id, String code, Date date, String iXPatientId) {
		return encounterDao.getDuplicateEncounters(id, code, date, iXPatientId);
	}

	@Override
	public void updateInteropXEncounterId(Integer id, String interopXEncounterId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		encounterDao.updateInteropXEncounterId(id, interopXEncounterId, processName, processStatus);
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		encounterDao.updateInteropXPatientId(interopXPatientId, actualPatientId,processingName,  processingStatus);
		
	}

	public List<Object> getUniqueEncountersByInteropXPatientId(String interopXPatientId) {
		return encounterDao.getUniqueEncountersByInteropXPatientId(interopXPatientId);
	}

}
