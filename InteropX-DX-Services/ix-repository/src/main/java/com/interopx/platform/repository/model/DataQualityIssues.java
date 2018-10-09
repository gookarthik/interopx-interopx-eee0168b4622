package com.interopx.platform.repository.model;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DataQualityIssues {
	
	private String categoryElement;
	private String categoryDescription;
	
	@JsonIgnore
	private int actualPoints;
	
	@JsonIgnore
	private int maxPoints;
	
	@JsonIgnore
	private float rubricScore;
	
	private int noOfissues;
	
	public String getCategoryElement() {
		return categoryElement;
	}
	public void setCategoryElement(String categoryElement) {
		this.categoryElement = categoryElement;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public int getActualPoints() {
		return actualPoints;
	}
	public void setActualPoints(int actualPoints) {
		this.actualPoints = actualPoints;
	}
	public int getMaxPoints() {
		return maxPoints;
	}
	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}
	public float getRubricScore() {
		return rubricScore;
	}
	public void setRubricScore(float rubricScore) {
		this.rubricScore = rubricScore;
	}
	public int getNoOfissues() {
		return noOfissues;
	}
	public void setNoOfissues(int noOfissues) {
		this.noOfissues = noOfissues;
	}
	

}
