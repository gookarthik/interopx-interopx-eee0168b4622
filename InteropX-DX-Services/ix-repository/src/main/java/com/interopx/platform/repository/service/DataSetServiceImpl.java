package com.interopx.platform.repository.service;

import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.DataSetDao;
import com.interopx.platform.repository.model.DataSet;
import com.interopx.platform.repository.model.DataSource;


@Service
@Transactional
@PropertySource(value = { "classpath:application.properties" })
public class DataSetServiceImpl implements DataSetService {

	@Autowired
	private DataSetDao dataSetDao;

	@Autowired
	private Environment environment;

	public DataSet saveOrUpdate(DataSet dataSet) {
		dataSetDao.saveOrUpdate(dataSet);
		for (DataSource dataSource : dataSet.getData_sources()) {
			String directory = environment.getRequiredProperty("directory") + dataSet.getDataSetName() + "-"
					+ dataSet.getDataSetId() + "\\" + dataSource.getDataSourceName() + "-"
					+ dataSource.getDataSourceId();
			File files = new File(directory);
			if (!files.exists()) {
				if (files.mkdirs()) {
					System.out.println("Multiple directories are created!");
				} else {
					System.out.println("Failed to create multiple directories!");
				}
			}
		}
		return dataSet;
	}

	public DataSet getDataSetById(Integer dsId) {
		return dataSetDao.getDataSetById(dsId);

	}

	public List<DataSet> getAllDataSets() {
		return dataSetDao.getAllDataSets();

	}

	public DataSet deleteDataSetById(Integer dsId) {
		return dataSetDao.deleteDataSetById(dsId);

	}

}
