package com.interopx.platform.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.GroupsDao;
import com.interopx.platform.repository.model.Groups;
import com.interopx.platform.repository.model.Pages;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class GroupsServiceImpl implements GroupsService{
	
	@Autowired
	private GroupsDao groupsDao;
	
	public Groups saveOrUpdate(Groups group) {
		return groupsDao.saveOrUpdate(group);
	}

	public List<Groups> getGroupsByInteropXPatientId(String interopXId) {
		return groupsDao.getGroupsByInteropXPatientId(interopXId);
	}

	@Override
	public List<Groups> getAllConflictGroups() {
		return groupsDao.getAllConflictGroups();
	}

	@Override
	public Pages getGroupsByPage(Integer currentPage, Integer pageSize) {
		return groupsDao.getGroupByPage(currentPage, pageSize);
	}

	@Override
	public Groups resolveConflicts(Groups group) {
		return groupsDao.resolveConflicts(group);
	}

	@Override
	public Groups excludeConflicts(Groups group, DataProcessingStatusEnum processName, Boolean processStatus) {
		return groupsDao.excludeConflicts(group, processName, processStatus);
	}

}
