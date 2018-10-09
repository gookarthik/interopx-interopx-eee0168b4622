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
@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class) })
@Table(name = "data_quality_information")
public class DataQualityInformation {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "resource_name")
	private String resourceName;

	@Column(name = "score")
	private Integer score;

	@Column(name = "ix_patient_id")
	private String ixPatientId;

	@Column(name = "record_id")
	private Integer recordId;

	@Column(name = "issue_list")
	@Type(type = "StringJsonObject")
	private String issueList;

	@Column(name = "last_updated_time")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedTime;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "data_quality_id")
	private DataQuality dataQuality;

	@Column(name = "no_of_issues")
	private Integer noOfIssues;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getIxPatientId() {
		return ixPatientId;
	}

	public void setIxPatientId(String ixPatientId) {
		this.ixPatientId = ixPatientId;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getIssueList() {
		return issueList;
	}

	public void setIssueList(String issueList) {
		this.issueList = issueList;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public DataQuality getDataQuality() {
		return dataQuality;
	}

	public void setDataQuality(DataQuality dataQuality) {
		this.dataQuality = dataQuality;
	}

	public Integer getNoOfIssues() {
		return noOfIssues;
	}

	public void setNoOfIssues(Integer noOfIssues) {
		this.noOfIssues = noOfIssues;
	}
}
