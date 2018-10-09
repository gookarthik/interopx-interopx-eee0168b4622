package com.interopx.platform.repository.resource;

import java.util.Date;

public class Smoking {

	private String patientId;
	private String encId;
	private String smokingStatusCode;
	private String smokingStatusCodeSystem;
	private String smokingStatusDisplayName;
	private Date startTime;
	private Date endDate;

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

	public String getSmokingStatusCode() {
		return smokingStatusCode;
	}

	public void setSmokingStatusCode(String smokingStatusCode) {
		this.smokingStatusCode = smokingStatusCode;
	}

	public String getSmokingStatusCodeSystem() {
		return smokingStatusCodeSystem;
	}

	public void setSmokingStatusCodeSystem(String smokingStatusCodeSystem) {
		this.smokingStatusCodeSystem = smokingStatusCodeSystem;
	}

	public String getSmokingStatusDisplayName() {
		return smokingStatusDisplayName;
	}

	public void setSmokingStatusDisplayName(String smokingStatusDisplayName) {
		this.smokingStatusDisplayName = smokingStatusDisplayName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
