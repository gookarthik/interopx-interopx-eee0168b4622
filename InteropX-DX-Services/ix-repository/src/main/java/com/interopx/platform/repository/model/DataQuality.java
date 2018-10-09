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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "data_quality")
public class DataQuality {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "et_id")
	private Integer etId;

	@Column(name = "ds_id")
	private Integer dsId;

	@Column(name = "overall_score")
	private Double overallScore;

	@Column(name = "no_of_issues")
	private Integer noOfIssues;

	@Column(name = "last_updated_time")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedTime;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "data_source_quality_id")
	private DataSourceQuality dataSourceQuality;

	@JsonManagedReference
	@OneToMany(mappedBy = "dataQuality", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<DataQualityInformation> resourceList = new ArrayList<DataQualityInformation>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEtId() {
		return etId;
	}

	public void setEtId(Integer etId) {
		this.etId = etId;
	}

	public Integer getDsId() {
		return dsId;
	}

	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}

	public Double getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(Double overallScore) {
		this.overallScore = overallScore;
	}

	public Integer getNoOfIssues() {
		return noOfIssues;
	}

	public void setNoOfIssues(Integer noOfIssues) {
		this.noOfIssues = noOfIssues;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public DataSourceQuality getDataSourceQuality() {
		return dataSourceQuality;
	}

	public void setDataSourceQuality(DataSourceQuality dataSourceQuality) {
		this.dataSourceQuality = dataSourceQuality;
	}

	public List<DataQualityInformation> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<DataQualityInformation> resourceList) {
		this.resourceList = resourceList;
	}
}