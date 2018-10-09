package com.interopx.platform.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "extraction")
public class ExtractionDetails {

	@Id
	@Column(name = "extraction_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer extractionId;

	@Column(name = "dataset_id")
	private Integer dataSetId;

	@Column(name = "dataset_name")
	private String dataSetName;

	@Column(name = "datasource_id")
	private Integer dataSourceId;

	@Column(name = "datasource_name")
	private String dataSourceName;

	@Column(name = "files_location")
	private String filesLocation;

	@Column(name = "status")
	private String status;
	
	@Column(name = "data_processing_status")
	@Type(type = "StringJsonObject")
	private String dataProcessingStatus;

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

	public String getDataProcessingStatus() {
		return dataProcessingStatus;
	}

	public void setDataProcessingStatus(String dataProcessingStatus) {
		this.dataProcessingStatus = dataProcessingStatus;
	}

}
