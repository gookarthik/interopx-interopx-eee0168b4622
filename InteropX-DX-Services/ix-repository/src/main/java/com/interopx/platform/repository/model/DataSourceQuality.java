package com.interopx.platform.repository.model;

import java.util.ArrayList;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "data_source_quality")
public class DataSourceQuality {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ds_id")
	private Integer dsId;

	@Column(name = "score")
	private Double score;

	@Column(name = "no_of_issues")
	private Integer noOfIssues;

	@Column(name = "data_source_name")
	private String dataSourceName;

	@JsonManagedReference
	@OneToMany(mappedBy = "dataSourceQuality", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<DataQuality> dataQualityList = new ArrayList<DataQuality>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDsId() {
		return dsId;
	}

	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getNoOfIssues() {
		return noOfIssues;
	}

	public void setNoOfIssues(Integer noOfIssues) {
		this.noOfIssues = noOfIssues;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public List<DataQuality> getDataQualityList() {
		return dataQualityList;
	}

	public void setDataQualityList(List<DataQuality> dataQualityList) {
		this.dataQualityList = dataQualityList;
	}
}