package com.interopx.platform.repository.service;

import java.util.List;

import com.interopx.platform.repository.model.Conflicts;

public interface ConflictsService {
	
	Conflicts saveOrUpdate(Conflicts conflict);
	
	List<Conflicts> getConflictsByInteropXPatientId(Integer groupId);
	
	List<Conflicts> getConflictsByExtractionIdAndInternalPatientId(Integer etId,String patientId);

	List<Conflicts> getConflictsByRecordIdAndExcluded(Integer groupId, Integer recordId, Boolean isExcluded);
}
