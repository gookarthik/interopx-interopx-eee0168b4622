package com.interopx.platform.dataquality.model;

import java.util.List;

import com.interopx.platform.repository.model.DataQuality;

public class QualityScoreOutput {
	
	private Integer extractionId;
	
	private Double score;
	
	private Integer issues;
	
	private Integer datasourceId;
	
	private String dataSourceName;
	
	private List<DataQuality> dataQuality;

	public Integer getExtractionId() {
		return extractionId;
	}

	public void setExtractionId(Integer extractionId) {
		this.extractionId = extractionId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getIssues() {
		return issues;
	}

	public void setIssues(Integer issues) {
		this.issues = issues;
	}

	public Integer getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(Integer datasourceId) {
		this.datasourceId = datasourceId;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public List<DataQuality> getDataQuality() {
		return dataQuality;
	}

	public void setDataQuality(List<DataQuality> dataQuality) {
		this.dataQuality = dataQuality;
	}
}
