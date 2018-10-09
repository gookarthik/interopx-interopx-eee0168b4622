package com.interopx.sample.repository.service;

import java.util.List;

import com.interopx.sample.repository.model.DataSource;


public interface DataSourceService {
	DataSource saveOrUpdate(DataSource dataSource);
	DataSource getDataSourceById(Integer dsId);
	List<DataSource> getAllDataSources();
}
