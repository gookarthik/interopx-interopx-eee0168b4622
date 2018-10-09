package com.interopx.platform.repository.resource;

import java.util.Date;

public class Patient {

	private String patientId;
	private String encId;
	private String HAOID;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private String dateOfBirth;
	private String language;
	private String raceCode;
	private String ethnicityCode;
	private String adminGender;
	private String aliveIndicator;
	private String deceasedDateTime;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String telecom;
	private String siteOID;
	private String patIdExtension;

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

	public String getHAOID() {
		return HAOID;
	}

	public void setHAOID(String hAOID) {
		HAOID = hAOID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getRaceCode() {
		return raceCode;
	}

	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}

	public String getEthnicityCode() {
		return ethnicityCode;
	}

	public void setEthnicityCode(String ethnicityCode) {
		this.ethnicityCode = ethnicityCode;
	}

	public String getAdminGender() {
		return adminGender;
	}

	public void setAdminGender(String adminGender) {
		this.adminGender = adminGender;
	}

	public String getAliveIndicator() {
		return aliveIndicator;
	}

	public void setAliveIndicator(String aliveIndicator) {
		this.aliveIndicator = aliveIndicator;
	}

	public String getDeceasedDateTime() {
		return deceasedDateTime;
	}

	public void setDeceasedDateTime(String deceasedDateTime) {
		this.deceasedDateTime = deceasedDateTime;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTelecom() {
		return telecom;
	}

	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}

	public String getSiteOID() {
		return siteOID;
	}

	public void setSiteOID(String siteOID) {
		this.siteOID = siteOID;
	}

	public String getPatIdExtension() {
		return patIdExtension;
	}

	public void setPatIdExtension(String patIdExtension) {
		this.patIdExtension = patIdExtension;
	}

}
