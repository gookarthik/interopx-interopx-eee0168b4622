package com.interopx.platform.repository.resource;

import java.util.Date;

public class Vitals {

	private String patientId;
	private String encId;
	private String vitalCode;
	private String codeSystem;
	private String description;
	private Date vitalsDate;
	private String vitals;
	private String units;

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

	public String getVitalCode() {
		return vitalCode;
	}

	public void setVitalCode(String vitalCode) {
		this.vitalCode = vitalCode;
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

	public Date getVitalsDate() {
		return vitalsDate;
	}

	public void setVitalsDate(Date vitalsDate) {
		this.vitalsDate = vitalsDate;
	}

	public String getVitals() {
		return vitals;
	}

	public void setVitals(String vitals) {
		this.vitals = vitals;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

}
