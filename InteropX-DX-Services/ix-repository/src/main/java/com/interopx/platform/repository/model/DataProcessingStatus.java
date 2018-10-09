package com.interopx.platform.repository.model;

public class DataProcessingStatus {
	
	private boolean transformationStatus;
	
	private boolean qualityStatus;
	
	private boolean idMatchingStatus;
	
	private boolean deDuplicationStatus;
	
	public DataProcessingStatus(boolean transformationStatus, boolean qualityStatus, boolean idMatchingStatus,
			boolean deDuplicationStatus) {
		super();
		this.transformationStatus = transformationStatus;
		this.qualityStatus = qualityStatus;
		this.idMatchingStatus = idMatchingStatus;
		this.deDuplicationStatus = deDuplicationStatus;
	}

	public Boolean getTransformationStatus() {
		return transformationStatus;
	}

	public void setTransformationStatus(Boolean transformationStatus) {
		this.transformationStatus = transformationStatus;
	}

	public Boolean getQualityStatus() {
		return qualityStatus;
	}

	public void setQualityStatus(Boolean qualityStatus) {
		this.qualityStatus = qualityStatus;
	}

	public Boolean getIdMatchingStatus() {
		return idMatchingStatus;
	}

	public void setIdMatchingStatus(Boolean idMatchingStatus) {
		this.idMatchingStatus = idMatchingStatus;
	}

	public Boolean getDeDuplicationStatus() {
		return deDuplicationStatus;
	}

	public void setDeDuplicationStatus(Boolean deDuplicationStatus) {
		this.deDuplicationStatus = deDuplicationStatus;
	}

	@Override
	public String toString() {
		return "{\"transformationStatus\":" + transformationStatus + ", \"qualityStatus\":" + qualityStatus
				+ ", \"idMatchingStatus\":" + idMatchingStatus + ", \"deDuplicationStatus\":" + deDuplicationStatus + "}";
	}
	
	
	
}
