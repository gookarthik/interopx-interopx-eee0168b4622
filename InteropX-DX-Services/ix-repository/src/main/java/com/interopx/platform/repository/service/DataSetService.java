package com.interopx.platform.repository.service;

import java.util.List;

import com.interopx.platform.repository.model.DataSet;


public interface DataSetService {
	
	DataSet saveOrUpdate(DataSet dataSet);

	DataSet getDataSetById(Integer dsId);

	List<DataSet> getAllDataSets();

	DataSet deleteDataSetById(Integer dsId);
}
