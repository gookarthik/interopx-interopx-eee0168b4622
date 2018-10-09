package com.interopx.platform.repository.dao;

import java.util.List;

import com.interopx.platform.repository.model.DataSet;
import com.interopx.platform.repository.model.ExtractionDetails;


public interface FlatFileConnectorDao {
	
	List<ExtractionDetails> getExtractionDetailsBydsId(Integer dsId);
	
	String updateStatusByETId(Integer etId,String status);
	
	DataSet getDataSetBydsId(Integer dsId);
	
	ExtractionDetails saveOrUpdate(ExtractionDetails extraction);

}
