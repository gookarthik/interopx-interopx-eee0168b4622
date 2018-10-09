package com.interopx.platform.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.interopx.platform.repository.configuration.JSONObjectUserType;


@Entity
@Table(name = "vital_signs")
@TypeDefs({@TypeDef(name = "StringJsonObject", typeClass =JSONObjectUserType.class)})
public class VitalSigns {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ds_id")
	private Integer dataSourceId;

	@Column(name = "extraction_id")
	private Integer extractionTaskId;
	
	@Column(name = "interopx_vitals_id")
	private String interopxVitalId;

	@Column(name = "interopx_patient_id")
	private String interopxPatientId;

	@Column(name = "actual_vitals_id")
	private String actualVitalId;

	@Column(name = "ds_name")
	private String dataSourceName;
	
	@Column(name = "category")
	private String category;

	@Column(name = "data")
	@Type(type = "StringJsonObject")
	private String data;

	@CreationTimestamp
	@Column(name = "last_updated_ts", columnDefinition = "TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedTs;
	
	@Column(name = "data_processing_status")
	@Type(type = "StringJsonObject")
	private String dataProcessingStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public Integer getExtractionTaskId() {
		return extractionTaskId;
	}

	public void setExtractionTaskId(Integer extractionTaskId) {
		this.extractionTaskId = extractionTaskId;
	}

	public String getInteropxVitalId() {
		return interopxVitalId;
	}

	public void setInteropxVitalId(String interopxVitalId) {
		this.interopxVitalId = interopxVitalId;
	}

	public String getInteropxPatientId() {
		return interopxPatientId;
	}

	public void setInteropxPatientId(String interopxPatientId) {
		this.interopxPatientId = interopxPatientId;
	}

	public String getActualVitalId() {
		return actualVitalId;
	}

	public void setActualVitalId(String actualVitalId) {
		this.actualVitalId = actualVitalId;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getLastUpdatedTs() {
		return lastUpdatedTs;
	}

	public void setLastUpdatedTs(Date lastUpdatedTs) {
		this.lastUpdatedTs = lastUpdatedTs;
	}

	public String getDataProcessingStatus() {
		return dataProcessingStatus;
	}

	public void setDataProcessingStatus(String dataProcessingStatus) {
		this.dataProcessingStatus = dataProcessingStatus;
	}

}
