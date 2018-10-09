package com.interopx.platform.repository.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.ExtractionDetails;

@Repository
public class ExtractionDaoImpl extends AbstractDao implements ExtractionDao{

	public List<ExtractionDetails> getExtractionDetailsBydsId(Integer dsId) {
		Criteria crit = getSession().createCriteria(ExtractionDetails.class);
		crit.add(Restrictions.eq("dataSourceId", dsId));
		return crit.list();
	}

	public ExtractionDetails getExtractionById(Integer etId) {
		
		Criteria crit = getSession().createCriteria(ExtractionDetails.class);
		crit.add(Restrictions.eq("extractionId", etId));
		ExtractionDetails ed = (ExtractionDetails) crit.uniqueResult();
		return ed;
	}
	
}
