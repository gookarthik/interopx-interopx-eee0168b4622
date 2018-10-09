package com.interopx.platform.repository.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.ProcedureDao;
import com.interopx.platform.repository.model.ProcedureResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class ProcedureServiceImpl implements ProcedureService {

	@Autowired
	private ProcedureDao procedureDao;

	public ProcedureResource saveOrUpdateProcedure(ProcedureResource procedure) {
		return procedureDao.saveOrUpdateProcedure(procedure);
	}

	public List<ProcedureResource> getAllProcedure() {
		return procedureDao.getAllProcedure();
	}

	public ProcedureResource getProcedureById(Integer id) {
		return procedureDao.getProcedureById(id);
	}
	public List<ProcedureResource> getProcedureByExtractionIdAndInternalPatientId(Integer etId, String patientId){
		return procedureDao.getProcedureByExtractionIdAndInternalPatientId(etId, patientId);
	}

	@Override
	public List<ProcedureResource> getProceduresByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		return procedureDao.getProceduresByEtIdAndProcessingStatus(etId, statusEnum, processStatus);
	}

	@Override
	public List<ProcedureResource> getDuplicateProcedures(Integer id, String code, Date date, String iXPatientId) {
		return procedureDao.getDuplicateProcedures(id, code, date, iXPatientId);
	}

	@Override
	public void updateInteropXProcedureId(Integer id, String interopXProcedureId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		procedureDao.updateInteropXProcedureId(id, interopXProcedureId, processName, processStatus);
	}
		
	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		procedureDao.updateInteropXPatientId(interopXPatientId, actualPatientId, processingName, processingStatus);
	}

	@Override
	public List<Object> getUniqueProceduresByInteropXPatientId(String interopXPatientId) {
		return procedureDao.getUniqueProceduresByInteropXPatientId(interopXPatientId);
	}

}
