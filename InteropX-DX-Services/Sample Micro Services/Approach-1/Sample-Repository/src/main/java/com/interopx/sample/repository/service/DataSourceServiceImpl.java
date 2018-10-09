package com.interopx.sample.repository.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.sample.repository.dao.DataSourceDao;
import com.interopx.sample.repository.model.DataSource;
@Service
@Transactional
public class DataSourceServiceImpl implements DataSourceService {
	@Autowired
	private DataSourceDao dataSourceDao;
	
	public DataSource saveOrUpdate(DataSource dataSource) {
		return dataSourceDao.saveOrUpdate(dataSource);
	}
	
	public DataSource getDataSourceById(Integer dsId) {
		return dataSourceDao.getDataSourceById(dsId);

	}
	public List<DataSource> getAllDataSources() {
		return dataSourceDao.getAllDataSources();

	}


}
