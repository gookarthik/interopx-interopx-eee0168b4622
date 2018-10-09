package com.interopx.platform.repository.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.interopx.platform.repository.configuration.JSONObjectUserType;

@Entity
@TypeDefs({@TypeDef(name = "StringJsonObject", typeClass = JSONObjectUserType.class)})
@Table(name = "conflict_groups")
public class Groups {
	
	@Id
	@Column(name = "group_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer groupId;
	
	@Column(name = "datasource_id")
	private Integer dataSourceId;
	
	@Column(name = "extraction_id")
	private Integer extractionId;
	
	@Column(name = "ix_patient_id")
	private String interopxId;
	
	@Column(name = "ix_patient_record")
	@Type(type = "StringJsonObject")
	private String interopxRecord;
	
	@Column(name = "queried_elements")
	@Type(type = "StringJsonObject")
	private String queriedElements;
	
	/*@Transient
    private List<Conflicts> conflicts = new ArrayList<Conflicts>();*/
	
	@JsonManagedReference
	@OneToMany(mappedBy = "groups", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Conflicts> conflicts = new ArrayList<Conflicts>();
	
	@Column(name="last_updated_ts")
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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
	public String getInteropxId() {
		return interopxId;
	}
	public void setInteropxId(String interopxId) {
		this.interopxId = interopxId;
	}
	public String getInteropxRecord() {
		return interopxRecord;
	}
	public void setInteropxRecord(String interopxRecord) {
		this.interopxRecord = interopxRecord;
	}
	public String getQueriedElements() {
		return queriedElements;
	}
	public void setQueriedElements(String queriedElements) {
		this.queriedElements = queriedElements;
	}
	public List<Conflicts> getConflicts() {
		return conflicts;
	}
	public void setConflicts(List<Conflicts> conflicts) {
		this.conflicts = conflicts;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
