package com.interopx.platform.repository.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.DataSet;
import com.interopx.platform.repository.model.ExtractionDetails;


@Repository
public class FlatFileConnectorDaoImpl extends AbstractDao implements FlatFileConnectorDao{
	
	public List<ExtractionDetails> getExtractionDetailsBydsId(Integer dsId) {
		Criteria criteria = getSession().createCriteria(ExtractionDetails.class)
                .add(Restrictions.eq("dataSourceId", dsId))
                .add(Restrictions.eq("status","Pending"));
        List<ExtractionDetails> extractionDetails = criteria.list();
		return extractionDetails;
	}
	
	
	public String updateStatusByETId(Integer etId,String status) {
		ExtractionDetails extraction = (ExtractionDetails) getSession().get(ExtractionDetails.class, etId);
		extraction.setStatus(status);
		getSession().saveOrUpdate(extraction);
		return "Updated Successfully";
	}
	
	public DataSet getDataSetBydsId(Integer dsId) {
		DataSet dataSet = (DataSet) getSession().get(DataSet.class, dsId);
		return dataSet;
	}
	
	public ExtractionDetails saveOrUpdate(ExtractionDetails extraction) {
		getSession().saveOrUpdate(extraction);
		return extraction;
	}
}
