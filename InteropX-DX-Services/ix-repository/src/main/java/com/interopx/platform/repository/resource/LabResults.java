package com.interopx.platform.repository.resource;

import java.util.Date;

public class LabResults {

	private String patientId;
	private String encId;
	private String testPanel;
	private String testPanelSystem;
	private String testPanelName;
	private String labType;
	private String labResultCodeSystem;
	private String originalName;
	private Date collectionDate;
	private String resultType;
	private String resultValue;
	private String resultUnits;
	private String resultMin;
	private String resultMax;
	private String refRange;

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

	public String getTestPanel() {
		return testPanel;
	}

	public void setTestPanel(String testPanel) {
		this.testPanel = testPanel;
	}

	public String getTestPanelSystem() {
		return testPanelSystem;
	}

	public void setTestPanelSystem(String testPanelSystem) {
		this.testPanelSystem = testPanelSystem;
	}

	public String getTestPanelName() {
		return testPanelName;
	}

	public void setTestPanelName(String testPanelName) {
		this.testPanelName = testPanelName;
	}

	public String getLabType() {
		return labType;
	}

	public void setLabType(String labType) {
		this.labType = labType;
	}

	public String getLabResultCodeSystem() {
		return labResultCodeSystem;
	}

	public void setLabResultCodeSystem(String labResultCodeSystem) {
		this.labResultCodeSystem = labResultCodeSystem;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public String getResultUnits() {
		return resultUnits;
	}

	public void setResultUnits(String resultUnits) {
		this.resultUnits = resultUnits;
	}

	public String getResultMin() {
		return resultMin;
	}

	public void setResultMin(String resultMin) {
		this.resultMin = resultMin;
	}

	public String getResultMax() {
		return resultMax;
	}

	public void setResultMax(String resultMax) {
		this.resultMax = resultMax;
	}

	public String getRefRange() {
		return refRange;
	}

	public void setRefRange(String refRange) {
		this.refRange = refRange;
	}

}
