package com.lms2ue1.sbsweb.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private String surname;
	private String lastname;
	private String organization;
	private String role;
	private String username;

	// ---- Associations ----//
	private List<Organization> organizations;
	private List<Role> roles;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public User() {
	}

	public User(long userId, ) {
		this.surname = surname;
		this.lastname = lastname;
		this.organization = organization;
		this.role = role;
		this.username = username;
	}

	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//
	public Long getId() {
		return this.id;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public String getOrganization() {
		return this.organization;
	}

	public String getRole() {
		return this.role;
	}

	public String getUsername() {
		return this.username;
	}

	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
	public void setId(Long id) {
		this.id = id;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}