package com.interopx.platform.WorkflowService.dao;

import org.hibernate.Session;

import com.interopx.platform.repository.dao.AbstractDao;

public class WorkflowRepositoryDAO extends AbstractDao{
	public Session getRepositorySession() {
		return getSession();
	}
}
