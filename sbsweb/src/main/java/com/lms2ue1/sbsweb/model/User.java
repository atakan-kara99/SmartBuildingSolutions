package com.lms2ue1.sbsweb.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * User class with associations to role and organizations.
 * 
 * @author juliusdaum
 */
@Entity
public class User {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String forename;
	private String lastname;
	private String role;
	private String username;

	// ---- Associations ----//
	@ManyToMany
	private List<Organization> organizations;
	@ManyToMany
	private List<Role> roles;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public User() {
	}


	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//
	public Long getId() {
		return this.id;
	}

	public String getForename() {
		return this.forename;
	}

	public String getLastname() {
		return this.lastname;
	}

	public String getRole() {
		return this.role;
	}

	public String getUsername() {
		return this.username;
	}

	public List<Organization> getOrganizations() {
		return this.organizations;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
	public void setId(Long id) {
		this.id = id;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}