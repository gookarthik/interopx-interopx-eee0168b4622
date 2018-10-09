package com.interopx.platform.repository.service;

import java.util.List;

import com.interopx.platform.repository.model.DataQuality;
import com.interopx.platform.repository.model.DataSourceQuality;

public interface DataQualityService {

	DataQuality saveOrUpdate(DataQuality dataQuality);
	
	List<DataQuality> getDataQualityByEtIdOrDsId(Integer etId, Integer dsId);
	
	Double getAllIssuesCountByEtIdOrDsId(Integer etId, Integer dsId);
	
	Double getAverageScoreByEtIdOrDsId(Integer etId, Integer dsId);

	List<DataQuality> getDataQualityByExtractionId();

	DataSourceQuality getDistinctDataSourceQualityByDataSourceId();
	
	DataSourceQuality saveOrUpdate(DataSourceQuality dataSourceQuality);

	DataSourceQuality getDataSourceQualityByDsId(Integer dsId);

	String getDataSourceNameByDsIdOrEtId(Integer extractionId, Integer datasourceId);

}
