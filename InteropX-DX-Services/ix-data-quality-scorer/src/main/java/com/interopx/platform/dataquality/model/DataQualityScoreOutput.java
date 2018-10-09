package com.interopx.platform.dataquality.model;

import java.util.List;

public class DataQualityScoreOutput {

	private Integer datasourceId;

	private Double score;

	private Integer issues;

	private String dataSourceName;

	private List<QualityScoreOutput> extractionList;

	public Integer getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(Integer datasourceId) {
		this.datasourceId = datasourceId;
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

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public List<QualityScoreOutput> getExtractionList() {
		return extractionList;
	}

	public void setExtractionList(List<QualityScoreOutput> extractionList) {
		this.extractionList = extractionList;
	}
}
