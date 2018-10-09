package com.interopx.platform.repository.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.Audit;

@Repository
public class AuditDaoImpl extends AbstractDao implements AuditDao {

	@Override
	public Audit saveOrUpdate(Audit audit) {
		getSession().saveOrUpdate(audit);
		return audit;
	}

	@Override
	public Audit getAuditById(Integer Id) {
		Audit audit = (Audit) getSession().get(Audit.class, Id);
		return audit;
	}

	@Override
	public List<Audit> getAllAudits() {
		List<Audit> auditList = getSession().createCriteria(Audit.class).list();
		return auditList;
	}
	
	@Override
	public Audit deleteAuditById(Integer Id) {
		Audit audit = (Audit) getSession().get(Audit.class, Id);
		getSession().delete(audit);
		return audit;
	}

}