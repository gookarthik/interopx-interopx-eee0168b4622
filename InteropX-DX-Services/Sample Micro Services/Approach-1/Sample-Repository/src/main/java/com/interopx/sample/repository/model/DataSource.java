package com.interopx.sample.repository.model;

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

@Entity
@Table(name = "data_sources")
public class DataSource {
	
	@Id
    @Column(name = "ds_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataSourceId;
	
	@Column(name="ds_name")
	private String dataSourceName;
	
	@Column(name="endpoint_url")
	private String endPointUrl;
	
	@Column(name="api")
	private String api;

	@Column(name="security_method")
	private String securityMethod;
	
	@Column(name="last_updated_ts")
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
	
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

	public String getEndPointUrl() {
		return endPointUrl;
	}

	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getSecurityMethod() {
		return securityMethod;
	}

	public void setSecurityMethod(String securityMethod) {
		this.securityMethod = securityMethod;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
