package com.interopx.sample.repository.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.interopx.sample.repository.model.DataSource;




@Repository
public class DataSourceDaoImpl extends AbstractDao implements DataSourceDao {

	@Override
	public DataSource saveOrUpdate(DataSource dataSource) {
		getSession().saveOrUpdate(dataSource);
		return dataSource;
	}

	@Override
	public DataSource getDataSourceById(Integer dataSourceId) {
		DataSource dataSource = (DataSource) getSession().get(DataSource.class, dataSourceId);
		return dataSource;
	}

	@Override
	public List<DataSource> getAllDataSources() {
		List<DataSource> dataSourceList = getSession().createCriteria(DataSource.class).list();
		return dataSourceList;
	}

}
