package com.interopx.platform.repository.dao;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.Conflicts;
import com.interopx.platform.repository.model.Groups;
import com.interopx.platform.repository.model.Pages;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Repository
public class GroupsDaoImpl extends AbstractDao implements GroupsDao{
	
	public Groups saveOrUpdate(Groups group) {
		getSession().saveOrUpdate(group);
		return group;
	}
	
	public List<Groups> getGroupsByInteropXPatientId(String interopXId) {
		Criteria criteria = getSession().createCriteria(Groups.class);
		criteria.add(Restrictions.eq("interopxId", interopXId));
		return criteria.list();
	}

	@Override
	public List<Groups> getAllConflictGroups() {
		Criteria criteria = getSession().createCriteria(Groups.class);
		return criteria.list();
	}

	@Override
	public Pages getGroupByPage(Integer currentPage, Integer pageSize) {
		Criteria criteria = getSession().createCriteria(Groups.class);
		criteria.setFirstResult(currentPage);
		criteria.setMaxResults(pageSize);
		Groups group = (Groups) criteria.uniqueResult();
		Pages page = new Pages();
		page.setPageSize(pageSize);
		page.setCurrentPage(currentPage);
		page.setTotalRecords(totalCount());
		page.getGroups().add(group);
		return page;
	}

	public Integer totalCount() {
		long count = 0l;
		try {
			Query query = getSession().createQuery(" select count(*) FROM Groups ");
			count = (long) ((Long) query.uniqueResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		int patientCount = Math.round(count);
		return patientCount;
	}

	@Override
	public Groups resolveConflicts(Groups group) {
		String interopXPatientId = group.getInteropxId();
		List<Conflicts> conflicts = group.getConflicts();
		
		for(Conflicts conflict: conflicts) {
			conflict.setStatus("Linked");
			conflict.setIs_excluded(false);
			Integer id = conflict.getRecordId();
			Query qry = getSession().createQuery("update PatientResource set interopx_patient_id=:interopXPatientId where id=:id");
	        qry.setParameter("id",id);
	        qry.setParameter("interopXPatientId", interopXPatientId);
			Integer res = qry.executeUpdate();
		}
		
		group.setConflicts(conflicts);
		getSession().saveOrUpdate(group);
		return group;
	}

	@Override
	public Groups excludeConflicts(Groups group,DataProcessingStatusEnum processName, Boolean processStatus) {
		List<Conflicts> conflicts = group.getConflicts();
		
		for(Conflicts conflict: conflicts) {
			conflict.setStatus("Excluded");
			conflict.setIs_excluded(true);
			Integer id = conflict.getRecordId();
			Query qry = getSession().createQuery("update PatientResource set data_processing_status = jsonb_set(to_jsonb(data_processing_status), '{"+processName.getName()+"}', to_jsonb("+processStatus+"), true)\r\n" + 
					"WHERE id=:id");
	        qry.setParameter("id",id);
			Integer res = qry.executeUpdate();
		}
		
		group.setConflicts(conflicts);
		getSession().saveOrUpdate(group);
		return group;
	}
	
}
