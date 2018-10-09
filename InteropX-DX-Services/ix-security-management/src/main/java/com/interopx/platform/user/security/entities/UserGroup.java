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
@Table(name = "user_groups")
public class UserGroup {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GROUP_SEQ")
	@SequenceGenerator(name = "USER_GROUP_SEQ", sequenceName = "user_group_id_seq", allocationSize=1)
	private Long id;
	
	@JsonIgnore
	@JoinColumn(name = "user_user_id", referencedColumnName = "user_id")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private User user;

	@JoinColumn(name = "group_group_id", referencedColumnName = "group_id")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Group group;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
