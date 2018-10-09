package com.interopx.sample.repository.dao;

import java.util.List;

import com.interopx.sample.repository.model.DataSource;



public interface DataSourceDao {
	
	DataSource saveOrUpdate(DataSource dataSource);
	
	DataSource getDataSourceById(Integer dsId);
	
	List<DataSource> getAllDataSources();

}
