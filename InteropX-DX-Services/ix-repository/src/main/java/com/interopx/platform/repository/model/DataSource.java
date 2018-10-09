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
@TypeDefs({@TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class)})
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
	
	@Column(name="is_secure")
	private Boolean isSecure;

	@Column(name="security_method")
	private String securityMethod;
	
	@Column(name="connector_type")
	private String connectorType;
	
	@Column(name="credentials")
	@Type(type = "StringJsonObject")
	private String credentials;
	
	@Column(name="ehr_admin_email")
	private String ehrAdminEmail;
	
	@Column(name="database_server")
	private String databaseServer;
	
	@Column(name="ehr_admin_contact")
	private String ehrAdminContact;
	
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

	public Boolean getIsSecure() {
		return isSecure;
	}

	public void setIsSecure(Boolean isSecure) {
		this.isSecure = isSecure;
	}

	public String getSecurityMethod() {
		return securityMethod;
	}

	public void setSecurityMethod(String securityMethod) {
		this.securityMethod = securityMethod;
	}
	public String getConnectorType() {
		return connectorType;
	}

	public void setConnectorType(String connectorType) {
		this.connectorType = connectorType;
	}

	public String getEhrAdminEmail() {
		return ehrAdminEmail;
	}

	public void setEhrAdminEmail(String ehrAdminEmail) {
		this.ehrAdminEmail = ehrAdminEmail;
	}

	public String getDatabaseServer() {
		return databaseServer;
	}

	public void setDatabaseServer(String databaseServer) {
		this.databaseServer = databaseServer;
	}

	public String getEhrAdminContact() {
		return ehrAdminContact;
	}

	public void setEhrAdminContact(String ehrAdminContact) {
		this.ehrAdminContact = ehrAdminContact;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
