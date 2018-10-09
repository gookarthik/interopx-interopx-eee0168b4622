package com.interopx.platform.repository.dao;

import java.util.List;

import com.interopx.platform.repository.model.Conflicts;

public interface ConflictsDao {
	
	Conflicts saveOrUpdate(Conflicts conflict);
	
	List<Conflicts> getConflictsByInteropXPatientId(Integer groupId);
	
	public List<Conflicts> getConditionResourcesByExtractionIdAndInternalPatientId(Integer etId,String patientId);
	
	public List<Conflicts> getConflictsByRecordIdAndExcluded(Integer groupId, Integer recordId, Boolean isExcluded);
}
