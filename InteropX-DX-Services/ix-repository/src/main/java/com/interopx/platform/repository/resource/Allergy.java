package com.interopx.platform.repository.resource;

import java.util.Date;

public class Allergy {

	private String patientId;
	private String encId;
	private String allergyType;
	private String allergyCode;
	private String codeSystem;
	private String allergyDescription;
	private Date allergyStartDate;
	private Date allergyStoppedDate;
	private Date allergyCreateTimestamp;
	private String status;
	private String allergyReaction;
	private String allergySeverity;

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

	public String getAllergyType() {
		return allergyType;
	}

	public void setAllergyType(String allergyType) {
		this.allergyType = allergyType;
	}

	public String getAllergyCode() {
		return allergyCode;
	}

	public void setAllergyCode(String allergyCode) {
		this.allergyCode = allergyCode;
	}

	public String getCodeSystem() {
		return codeSystem;
	}

	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}

	public String getAllergyDescription() {
		return allergyDescription;
	}

	public void setAllergyDescription(String allergyDescription) {
		this.allergyDescription = allergyDescription;
	}

	public Date getAllergyStartDate() {
		return allergyStartDate;
	}

	public void setAllergyStartDate(Date allergyStartDate) {
		this.allergyStartDate = allergyStartDate;
	}

	public Date getAllergyStoppedDate() {
		return allergyStoppedDate;
	}

	public void setAllergyStoppedDate(Date allergyStoppedDate) {
		this.allergyStoppedDate = allergyStoppedDate;
	}

	public Date getAllergyCreateTimestamp() {
		return allergyCreateTimestamp;
	}

	public void setAllergyCreateTimestamp(Date allergyCreateTimestamp) {
		this.allergyCreateTimestamp = allergyCreateTimestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAllergyReaction() {
		return allergyReaction;
	}

	public void setAllergyReaction(String allergyReaction) {
		this.allergyReaction = allergyReaction;
	}

	public String getAllergySeverity() {
		return allergySeverity;
	}

	public void setAllergySeverity(String allergySeverity) {
		this.allergySeverity = allergySeverity;
	}

}
