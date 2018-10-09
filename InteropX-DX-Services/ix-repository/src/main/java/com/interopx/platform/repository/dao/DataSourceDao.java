package com.interopx.platform.repository.dao;

import java.util.List;

import com.interopx.platform.repository.model.DataSource;




public interface DataSourceDao {
	
	DataSource saveOrUpdate(DataSource dataSource);
	
	DataSource getDataSourceById(Integer dsId);
	
	List<DataSource> getAllDataSources();
	
	DataSource deleteDataSourceById(Integer dsId);

}
