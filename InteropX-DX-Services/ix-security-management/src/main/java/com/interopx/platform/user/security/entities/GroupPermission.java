package com.interopx.platform.user.security.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "group_permissions")
public class GroupPermission {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUP_PERMISSION_SEQ")
	@SequenceGenerator(name = "GROUP_PERMISSION_SEQ", sequenceName = "group_permission_id_seq", allocationSize=1)
	private Long id;
	
	@JsonIgnore
	@JoinColumn(name = "group_group_id", referencedColumnName = "group_id")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Group group;

	@JoinColumn(name = "permission_permission_id", referencedColumnName = "permission_id")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Permission permission;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
}
