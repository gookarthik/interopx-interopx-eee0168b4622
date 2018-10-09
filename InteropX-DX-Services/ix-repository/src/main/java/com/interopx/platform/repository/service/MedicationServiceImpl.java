package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.MedicationDao;
import com.interopx.platform.repository.model.MedicationResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {

	@Autowired
	private MedicationDao medicationDao;

	public MedicationResource saveOrUpdateMedication(MedicationResource medication) {
		return medicationDao.saveOrUpdateMedication(medication);
	}

	public List<MedicationResource> getAllMedication() {
		return medicationDao.getAllMedication();
	}

	public MedicationResource getMedicationById(Integer id) {
		return medicationDao.getMedicationById(id);
	}
	public List<MedicationResource> getMedicationStatementsByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		return medicationDao.getMedicationStatementsByExtractionIdAndInternalPatientId(etId, patientId);
	}

	@Override
	public List<MedicationResource> getMedicationsByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		return medicationDao.getMedicationsByEtIdAndProcessingStatus(etId, statusEnum, processStatus);
	}

	@Override
	public List<MedicationResource> getDuplicateMedicaitons(Integer id, String code, Date date, String iXPatientId) {
		return medicationDao.getDuplicateMedicaitons(id, code, date, iXPatientId);
	}

	@Override
	public void updateInteropXMedicationId(Integer id, String interopXMedicationId,
			DataProcessingStatusEnum processName, Boolean processStatus) {
		medicationDao.updateInteropXMedicationId(id, interopXMedicationId, processName, processStatus);
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		medicationDao.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
	}

	@Override
	public List<Object> getUniqueMedicationsByInteropXPatientId(String interopXPatientId) {
		return medicationDao.getUniqueMedicationsByInteropXPatientId(interopXPatientId);
	}
}
