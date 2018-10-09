package com.interopx.platform.repository.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.DataQualityDao;
import com.interopx.platform.repository.model.DataQuality;
import com.interopx.platform.repository.model.DataQualityInformation;
import com.interopx.platform.repository.model.DataSourceQuality;

@Service
@Transactional
public class DataQualityServiceImpl implements DataQualityService {

	@Autowired
	private DataQualityDao dataQualityDao;

	@Override
	public DataQuality saveOrUpdate(DataQuality dataQuality) {
		return dataQualityDao.saveOrUpdate(dataQuality);
	}

	@Override
	public List<DataQuality> getDataQualityByEtIdOrDsId(Integer etId, Integer dsId) {
		return dataQualityDao.getDataQualityByEtIdOrDsId(etId, dsId);
	}

	@Override
	public Double getAllIssuesCountByEtIdOrDsId(Integer etId, Integer dsId) {
		return dataQualityDao.getAllIssuesCountByEtIdOrDsId(etId, dsId);
	}

	@Override
	public Double getAverageScoreByEtIdOrDsId(Integer etId, Integer dsId) {
		return dataQualityDao.getAverageScoreByEtIdOrDsId(etId, dsId);
	}

	@Override
	public List<DataQuality> getDataQualityByExtractionId() {
		return dataQualityDao.getDataQualityByExtractionId();
	}

	@Override
	public DataSourceQuality getDistinctDataSourceQualityByDataSourceId() {
		return dataQualityDao.getDistinctDataSourceQualityByDataSourceId();
	}

	@Override
	public DataSourceQuality saveOrUpdate(DataSourceQuality dataSourceQuality) {
		return dataQualityDao.saveOrUpdate(dataSourceQuality);
	}

	@Override
	public DataSourceQuality getDataSourceQualityByDsId(Integer dsId) {
		return dataQualityDao.getDataSourceQualityByDsId(dsId);
	}

	@Override
	public String getDataSourceNameByDsIdOrEtId(Integer extractionId, Integer datasourceId) {
		return dataQualityDao.getDataSourceNameByDsIdOrEtId(extractionId, datasourceId);
	}

}
