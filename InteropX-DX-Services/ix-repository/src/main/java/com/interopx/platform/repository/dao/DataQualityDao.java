package com.interopx.platform.repository.dao;

import java.util.List;

import com.interopx.platform.repository.model.DataQuality;
import com.interopx.platform.repository.model.DataSourceQuality;

public interface DataQualityDao {
	
	DataQuality saveOrUpdate(DataQuality dataQuality);
	
	List<DataQuality> getDataQualityByEtIdOrDsId(Integer etId, Integer dsId);
	
	Double getAllIssuesCountByEtIdOrDsId(Integer etId, Integer dsId);
	
	Double getAverageScoreByEtIdOrDsId(Integer etId, Integer dsId);

	public List<DataQuality> getDataQualityByExtractionId();

	DataSourceQuality saveOrUpdate(DataSourceQuality dataSourceQuality);
	
	DataSourceQuality getDataSourceQualityByDsId(Integer dsId);

	String getDataSourceNameByDsIdOrEtId(Integer extractionId, Integer datasourceId);

	DataSourceQuality getDistinctDataSourceQualityByDataSourceId();
	
}
