package com.interopx.platform.repository.resource;

import java.util.Date;

public class Procedure {

	private String patientId;
	private String encId;
	private String ccCptCode;
	private String codeSystem;
	private String description;
	private Date ccDateOfService;
	private String tencounterid;
	private String targetSiteCode;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getEncId() {
		return encId;
	}

	public void setEncId(String encId) {
		this.encId = encId;
	}

	public String getCcCptCode() {
		return ccCptCode;
	}

	public void setCcCptCode(String ccCptCode) {
		this.ccCptCode = ccCptCode;
	}

	public String getCodeSystem() {
		return codeSystem;
	}

	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCcDateOfService() {
		return ccDateOfService;
	}

	public void setCcDateOfService(Date ccDateOfService) {
		this.ccDateOfService = ccDateOfService;
	}

	public String getTencounterid() {
		return tencounterid;
	}

	public void setTencounterid(String tencounterid) {
		this.tencounterid = tencounterid;
	}

	public String getTargetSiteCode() {
		return targetSiteCode;
	}

	public void setTargetSiteCode(String targetSiteCode) {
		this.targetSiteCode = targetSiteCode;
	}

}
