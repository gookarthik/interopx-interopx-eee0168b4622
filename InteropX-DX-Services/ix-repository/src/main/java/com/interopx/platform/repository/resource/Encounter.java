package com.interopx.platform.repository.resource;

import java.util.Date;

public class Encounter {

	private String tencounterid;
	private String patientId;
	private String encId;
	private String providerId;
	private String providerFullName;
	private Date startTime;
	private Date endTime;
	private String encounterCode;
	private String encounterCodeSystem;
	private String encounterCodeDisplayName;
	private String encounterLocationId;

	public String getTencounterid() {
		return tencounterid;
	}

	public void setTencounterid(String tencounterid) {
		this.tencounterid = tencounterid;
	}

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

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderFullName() {
		return providerFullName;
	}

	public void setProviderFullName(String providerFullName) {
		this.providerFullName = providerFullName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEncounterCode() {
		return encounterCode;
	}

	public void setEncounterCode(String encounterCode) {
		this.encounterCode = encounterCode;
	}

	public String getEncounterCodeSystem() {
		return encounterCodeSystem;
	}

	public void setEncounterCodeSystem(String encounterCodeSystem) {
		this.encounterCodeSystem = encounterCodeSystem;
	}

	public String getEncounterCodeDisplayName() {
		return encounterCodeDisplayName;
	}

	public void setEncounterCodeDisplayName(String encounterCodeDisplayName) {
		this.encounterCodeDisplayName = encounterCodeDisplayName;
	}

	public String getEncounterLocationId() {
		return encounterLocationId;
	}

	public void setEncounterLocationId(String encounterLocationId) {
		this.encounterLocationId = encounterLocationId;
	}

}
