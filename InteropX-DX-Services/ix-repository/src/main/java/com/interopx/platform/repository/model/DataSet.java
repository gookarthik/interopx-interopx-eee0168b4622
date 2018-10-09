package com.interopx.platform.repository.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "data_sets")
public class DataSet {

	@Id
	@Column(name = "dataset_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dataSetId;

	@Column(name = "dataset_name")
	private String dataSetName;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "dataset_datasource", joinColumns = { @JoinColumn(name = "dataset_id") }, inverseJoinColumns = { @JoinColumn(name = "ds_id") })
	private Set<DataSource> data_sources = new HashSet<DataSource>(0);

	@Column(name = "schedule_frequency")
	private String scheduleFrequency;

	@Column(name = "scheduled_time")
	private String scheduleTime;

	@Column(name = "last_updated_ts")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;

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

	public Set<DataSource> getData_sources() {
		return data_sources;
	}

	public void setData_sources(Set<DataSource> data_sources) {
		this.data_sources = data_sources;
	}

	public String getScheduleFrequency() {
		return scheduleFrequency;
	}

	public void setScheduleFrequency(String scheduleFrequency) {
		this.scheduleFrequency = scheduleFrequency;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
