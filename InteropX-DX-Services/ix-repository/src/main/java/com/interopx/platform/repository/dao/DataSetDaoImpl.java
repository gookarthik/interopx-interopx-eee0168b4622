package com.interopx.platform.repository.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.interopx.platform.repository.model.DataSet;


@Repository
public class DataSetDaoImpl extends AbstractDao implements DataSetDao {

	public DataSet saveOrUpdate(DataSet dataSet) {
		getSession().saveOrUpdate(dataSet);
		return dataSet;
	}

	public DataSet getDataSetById(Integer dataSourceId) {
		DataSet dataSource = (DataSet) getSession().get(DataSet.class, dataSourceId);
		return dataSource;
	}

	public List<DataSet> getAllDataSets() {
		Criteria criteria = getSession().createCriteria(DataSet.class);
		List<DataSet> dataSetsList = criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY).list();
		return dataSetsList;
	}
	
	public DataSet deleteDataSetById(Integer dataSetId) {
		DataSet dataSet = (DataSet) getSession().get(DataSet.class, dataSetId);
		dataSet.setData_sources(null);
		getSession().delete(dataSet);
		return dataSet;
	}

}
