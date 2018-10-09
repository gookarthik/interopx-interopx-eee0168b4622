package com.interopx.platform.repository.service;

import java.util.List;

import com.interopx.platform.repository.model.Groups;
import com.interopx.platform.repository.model.Pages;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface GroupsService {

	Groups saveOrUpdate(Groups group);
	
	List<Groups> getGroupsByInteropXPatientId(String interopXId);
	
	public List<Groups> getAllConflictGroups();
	
	Pages getGroupsByPage(Integer currentPage, Integer pageSize);
	
	Groups resolveConflicts(Groups group);
	
	Groups excludeConflicts(Groups group,DataProcessingStatusEnum processName, Boolean processStatus);
}
