package com.interopx.platform.repository.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.Conflicts;

@Repository
public class ConflictsDaoImpl extends AbstractDao implements ConflictsDao {

	public Conflicts saveOrUpdate(Conflicts conflict) {
		getSession().saveOrUpdate(conflict);
		return conflict;
	}

	@Override
	public List<Conflicts> getConflictsByInteropXPatientId(Integer groupId) {
		Criteria criteria = getSession().createCriteria(Conflicts.class);
		criteria.add(Restrictions.eq("groupId", groupId));
		return criteria.list();
	}

	public List<Conflicts> getConditionResourcesByExtractionIdAndInternalPatientId(Integer etId, String patientId) {
		Criteria criteria = getSession().createCriteria(Conflicts.class);
		criteria.add(Restrictions.eq("extractionId", etId));
		if (patientId != null) {
			criteria.add(Restrictions.eq("interopxPatientId", patientId));
		}
		return criteria.list();
	}

	@Override
	public List<Conflicts> getConflictsByRecordIdAndExcluded(Integer groupId, Integer recordId, Boolean isExcluded) {
		Criteria criteria = getSession().createCriteria(Conflicts.class, "conflict").createAlias("conflict.groups",
				"dp");
		if (groupId != null)
			criteria.add(Restrictions.eq("dp.groupId", groupId));
		criteria.add(Restrictions.eq("recordId", recordId));
		if (isExcluded != null) {
			criteria.add(Restrictions.eq("is_excluded", isExcluded));
		}
		return criteria.list();
	}

}
