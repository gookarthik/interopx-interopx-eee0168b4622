package com.interopx.platform.dataquality.service;

import java.util.List;

import com.interopx.platform.dataquality.model.DataQualityScoreOutput;
import com.interopx.platform.dataquality.model.QualityScoreOutput;
import com.interopx.platform.repository.model.DataQuality;
import com.interopx.platform.repository.model.DataSourceQuality;
import com.interopx.platform.repository.model.PatientResource;

public interface DataQualityScorerService {
	
	public List<PatientResource> processDataQualityChecking(Integer extractionId);
	
	public QualityScoreOutput getQualityScoreByEtIdOrDsId(Integer extractionId, Integer dsId);
	
	public List<DataQuality> getDataQualityByExtractionId();

	public DataQualityScoreOutput getDataQualityScoreByDsId(Integer dsId);

	public DataSourceQuality getDistinctDataSourceQualityByDataSourceId();
	
}
