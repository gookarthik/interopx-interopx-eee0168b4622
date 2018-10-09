package com.interopx.platform.repository.resource;

import java.util.Date;

public class Immunization {

	private String patientId;
	private String encId;
	private String immCode;
	private String codeSystem;
	private String description;
	private Date completedDate;
	private String immSite;
	private String lotNumber;

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	private String manufacturer;
	private String refused; // Refused/Cancelled/Administered;
	private String notes;

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

	public String getImmCode() {
		return immCode;
	}

	public void setImmCode(String immCode) {
		this.immCode = immCode;
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

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getImmSite() {
		return immSite;
	}

	public void setImmSite(String immSite) {
		this.immSite = immSite;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getRefused() {
		return refused;
	}

	public void setRefused(String refused) {
		this.refused = refused;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
