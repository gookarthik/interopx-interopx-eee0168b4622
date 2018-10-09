package com.interopx.platform.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.AuditDao;
import com.interopx.platform.repository.model.Audit;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {
	@Autowired
	private AuditDao auditDao;
	
	public Audit saveOrUpdate(Audit dataSource) {
		return auditDao.saveOrUpdate(dataSource);
	}
	
	public Audit getAuditById(Integer dsId) {
		return auditDao.getAuditById(dsId);

	}
	public List<Audit> getAllAudits() {
		return auditDao.getAllAudits();

	}
	
	public Audit deleteAuditById(Integer dsId) {
		return auditDao.deleteAuditById(dsId);

	}

}
