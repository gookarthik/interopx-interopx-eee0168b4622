package com.interopx.platform.agent.model;


public class ExtractionInfo {

	private Integer extractionId;

	private Integer dataSetId;

	private String dataSetName;

	private Integer dataSourceId;

	private String dataSourceName;

	private String filesLocation;

	private String status;

	public Integer getExtractionId() {
		return extractionId;
	}

	public void setExtractionId(Integer extractionId) {
		this.extractionId = extractionId;
	}

	public Integer getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(Integer dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getDataSetName() {
		return dataSetName;
	}

	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}

	public Integer getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getFilesLocation() {
		return filesLocation;
	}

	public void setFilesLocation(String filesLocation) {
		this.filesLocation = filesLocation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
