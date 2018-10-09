package com.interopx.platform.user.security.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "group_details", uniqueConstraints = @UniqueConstraint(columnNames = { "group_name" }))
public class Group {
	
	public Group() {
		
	}
	
	public Group(Long groupId) {
		this.groupId = groupId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUP_DETAILS_SEQ")
	@SequenceGenerator(name = "GROUP_DETAILS_SEQ", sequenceName = "group_details_id_seq", allocationSize = 1)
	@Column(name = "group_id")
	private Long groupId;

	@NotNull
	@Size(min = 4, max = 30)
	@Column(name = "group_name")
	private String groupName;

	@NotNull
	@Size(min = 4, max = 30)
	@Column(name = "group_desc")
	private String groupDesc;

	@Column(name = "create_user_name")
	private String createdUserName;

	@Column(name = "update_user_name")
	private String updatedUserName;
	
	@Column(name = "group_enabled")
	private Boolean groupEnabled; 
	
	
	@CreationTimestamp
	@Column(name = "create_timestamp")
	private Date createDateTime;
	
	@UpdateTimestamp
	@Column(name = "update_timestamp")
	private Date updateTime;
	
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.EAGER)
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<GroupPermission> groupPermissions = new HashSet<GroupPermission>();

	
	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getUpdatedUserName() {
		return updatedUserName;
	}

	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}

	public Boolean getGroupEnabled() {
		return groupEnabled;
	}

	public void setGroupEnabled(Boolean groupEnabled) {
		this.groupEnabled = groupEnabled;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set<GroupPermission> getGroupPermissions() {
		return groupPermissions;
	}

	public void setGroupPermissions(Set<GroupPermission> groupPermissions) {
		this.groupPermissions = groupPermissions;
	}
}
