package com.interopx.platform.user.security.entities;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "permissions", uniqueConstraints = @UniqueConstraint(columnNames = { "permission_name" }))
public class Permission{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISSION_SEQ")
	@SequenceGenerator(name = "PERMISSION_SEQ", sequenceName = "permission_id_seq", allocationSize = 1)
	@Column(name = "permission_id")
	private Long permissionId;
	
	@Column(name = "permission_name")
	private String authority;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "permission", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
	private Set<GroupPermission> groupPermissions = new HashSet<>();

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Set<GroupPermission> getGroupPermissions() {
		return groupPermissions;
	}

	public void setGroupPermissions(Set<GroupPermission> groupPermissions) {
		this.groupPermissions = groupPermissions;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}
}
