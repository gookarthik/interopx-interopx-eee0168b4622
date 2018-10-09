package com.interopx.platform.repository.dao;

import java.util.List;

import com.interopx.platform.repository.model.DataSet;





public interface DataSetDao {
	
	DataSet saveOrUpdate(DataSet dataSet);
	
	DataSet getDataSetById(Integer dsId);
	
	List<DataSet> getAllDataSets();
	
	DataSet deleteDataSetById(Integer dsId);

}
