package com.interopx.platform.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.ConflictsDao;
import com.interopx.platform.repository.model.Conflicts;

@Service
@Transactional
public class ConflictsServiceImpl implements ConflictsService{
	
	@Autowired
	private ConflictsDao conflictsDao;
	
	public Conflicts saveOrUpdate(Conflicts conflict) {
		return conflictsDao.saveOrUpdate(conflict);
	}

	@Override
	public List<Conflicts> getConflictsByInteropXPatientId(Integer groupId) {
		return conflictsDao.getConflictsByInteropXPatientId(groupId);
	}
	
	public List<Conflicts> getConflictsByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		return conflictsDao.getConditionResourcesByExtractionIdAndInternalPatientId(etId, patientId);
	}

	@Override
	public List<Conflicts> getConflictsByRecordIdAndExcluded(Integer groupId, Integer recordId, Boolean isExcluded) {
		return conflictsDao.getConflictsByRecordIdAndExcluded(groupId, recordId, isExcluded);
	}


}
