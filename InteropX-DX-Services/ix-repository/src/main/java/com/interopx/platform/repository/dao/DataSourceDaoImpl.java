package com.interopx.platform.repository.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.DataSource;





@Repository
public class DataSourceDaoImpl extends AbstractDao implements DataSourceDao {

	public DataSource saveOrUpdate(DataSource dataSource) {
		getSession().saveOrUpdate(dataSource);
		return dataSource;
	}

	public DataSource getDataSourceById(Integer dataSourceId) {
		DataSource dataSource = (DataSource) getSession().get(DataSource.class, dataSourceId);
		return dataSource;
	}

	public List<DataSource> getAllDataSources() {
		List<DataSource> dataSourceList = getSession().createCriteria(DataSource.class).list();
		return dataSourceList;
	}
	
	public DataSource deleteDataSourceById(Integer dataSourceId) {
		DataSource dataSource = (DataSource) getSession().get(DataSource.class, dataSourceId);
		getSession().delete(dataSource);
		return dataSource;
	}

}