package com.interopx.platform.connector.fhir.model;

public class FHIRAPIInfo {
	
    private Integer dataSourceId;
    
    private Integer extractionId;
	
	private String endPointUrl;
	
	private String securityMethod;
	
	private Boolean isSecured;
	
	private String accessToken;

	public Integer getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public Integer getExtractionId() {
		return extractionId;
	}

	public void setExtractionId(Integer extractionId) {
		this.extractionId = extractionId;
	}

	public String getEndPointUrl() {
		return endPointUrl;
	}

	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}

	public String getSecurityMethod() {
		return securityMethod;
	}

	public void setSecurityMethod(String securityMethod) {
		this.securityMethod = securityMethod;
	}

	public Boolean getIsSecured() {
		return isSecured;
	}

	public void setIsSecured(Boolean isSecured) {
		this.isSecured = isSecured;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
