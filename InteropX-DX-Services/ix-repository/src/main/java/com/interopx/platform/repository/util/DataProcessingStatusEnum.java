package com.interopx.platform.repository.util;

public enum DataProcessingStatusEnum {

	TRANSFORMATION("transformationStatus"),

	DATAQUALITYSCORER("qualityStatus"),

	PATIENTIDMATCHINGANDLINKING("idMatchingStatus"),

	DEDUPLICATION("deDuplicationStatus");

	private String name;

	DataProcessingStatusEnum(String processName) {
		this.name = processName;
	}

	public String getName() {
		return name;
	}

}
