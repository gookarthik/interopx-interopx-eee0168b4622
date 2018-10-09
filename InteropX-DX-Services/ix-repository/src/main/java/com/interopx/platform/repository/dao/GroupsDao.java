package com.interopx.platform.repository.dao;

import java.util.List;

import com.interopx.platform.repository.model.Groups;
import com.interopx.platform.repository.model.Pages;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface GroupsDao {
	
	Groups saveOrUpdate(Groups group);
	
	List<Groups> getGroupsByInteropXPatientId(String interopXId);
	
	List<Groups> getAllConflictGroups();
	
	Pages getGroupByPage(Integer currentPage,Integer pageSize);
	
	Groups resolveConflicts(Groups group);
	
	Groups excludeConflicts(Groups group,DataProcessingStatusEnum processName, Boolean processStatus);

}
