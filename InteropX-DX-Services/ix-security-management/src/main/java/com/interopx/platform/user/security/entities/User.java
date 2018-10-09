package com.interopx.platform.user.security.entities;

import java.util.Collection;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "user_details", uniqueConstraints = @UniqueConstraint(columnNames = { "user_name" }))
public class User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User() {
	}
	
	public User(Long userId) {
		this.userId = userId;
	}

	public User(String username) {
		this.username = username;
	}

	public User(String username, Date expires) {
		this.username = username;
		this.expires = expires.getTime();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_DETAILS_SEQ")
	@SequenceGenerator(name = "USER_DETAILS_SEQ", sequenceName = "user_details_id_seq", allocationSize=1)
	@Column(name = "user_id")
	private Long userId;

	@NotNull
	@Size(min = 4, max = 30)
	@Column(name = "user_name")
	private String username;

	@NotNull
	@Size(min = 4)
	@JsonIgnore
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email_address")
	private String email;

	@Transient
	private long expires;

	@NotNull
	@Column(name = "account_expired")
	private Boolean accountExpired;

	@NotNull
	@Column(name = "account_locked")
	private Boolean accountLocked;

	@NotNull
	@Column(name = "credentials_expired")
	private Boolean credentialsExpired;

	@NotNull
	@Column(name = "account_enabled")
	private Boolean accountEnabled;

	@Transient
	private String newPassword;

	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
	private Set<UserGroup> userGroups = new HashSet<>();
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}

	@JsonProperty
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@JsonIgnore
	public boolean isEnabled() {
		return accountEnabled;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public Boolean getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Boolean getCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(Boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public Boolean getAccountEnabled() {
		return accountEnabled;
	}

	public void setAccountEnabled(Boolean accountEnabled) {
		this.accountEnabled = accountEnabled;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getUsername();
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Set<UserGroup> userGroups) {

		return getGrantedAuthorities(getPermissions(userGroups));
	}

	private Set<String> getPermissions(Set<UserGroup> userGroups) {

		Set<String> permissions = new HashSet<>();
		Set<GroupPermission> groupPermissions;
		for (UserGroup userGroup : userGroups) {
			 Group group = userGroup.getGroup();
			 groupPermissions = group.getGroupPermissions();
			 for (GroupPermission groupPermission : groupPermissions) {
				 permissions.add(groupPermission.getPermission().getAuthority());
			 }
		}
		return permissions;
	}

	private Set<GrantedAuthority> getGrantedAuthorities(Set<String> privileges) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}
	
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return getAuthorities(this.userGroups);
	}
}
