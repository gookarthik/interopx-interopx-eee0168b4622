package com.interopx.platform.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.interopx.platform.repository.configuration.JSONObjectUserType;

@Entity
@TypeDefs({@TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class)})
@Table(name = "conflicts")
public class Conflicts {
	
	@Id
	@Column(name = "conflict_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer conflictId;
	
	@Column(name = "datasource_id")
	private Integer dataSourceId;
	
	@Column(name = "extraction_id")
	private Integer extractionId;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "group_id")
	private Groups groups;
	
	@Column(name = "record_id")
	private Integer recordId;
	
	@Column(name = "matched_elements")
	@Type(type = "StringJsonObject")
	private String matchedElements;
	
	@Column(name = "mismatched_elements")
	@Type(type = "StringJsonObject")
	private String misMatchedElements;
	
	@Column(name = "conflicted_record")
	@Type(type = "StringJsonObject")
	private String conflictedRecord;
	
	@Column(name = "score")
	private float score;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "is_excluded")
	private Boolean is_excluded;
	
	@Column(name="last_updated_ts")
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	
	
	public Integer getConflictId() {
		return conflictId;
	}
	public void setConflictId(Integer conflictId) {
		this.conflictId = conflictId;
	}
	public Integer getDataSourceId() {
		return dataSourceId;
	}
	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	public Integer getExtractionId() {
		return extractionId;
	}
	public void setExtractionId(Integer extractionId) {
		this.extractionId = extractionId;
	}
	public Groups getGroups() {
		return groups;
	}
	public void setGroups(Groups groups) {
		this.groups = groups;
	}
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public String getMatchedElements() {
		return matchedElements;
	}
	public void setMatchedElements(String matchedElements) {
		this.matchedElements = matchedElements;
	}
	public String getMisMatchedElements() {
		return misMatchedElements;
	}
	public void setMisMatchedElements(String misMatchedElements) {
		this.misMatchedElements = misMatchedElements;
	}
	public String getConflictedRecord() {
		return conflictedRecord;
	}
	public void setConflictedRecord(String conflictedRecord) {
		this.conflictedRecord = conflictedRecord;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getIs_excluded() {
		return is_excluded;
	}
	public void setIs_excluded(Boolean is_excluded) {
		this.is_excluded = is_excluded;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
