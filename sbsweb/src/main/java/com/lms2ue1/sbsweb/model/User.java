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
@Table(name="users")
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
	// TODO: set the associations
	private List<Organization> organizations;
	private List<Role> roles;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public User() {
	}

	/**
	 * Initializes a user object.
	 * 
	 * @param id            the unique id of the user.
	 * @param forename
	 * @param lastname
	 * @param role          the associated role.
	 * @param username
	 * @param organizations associated organizations.
	 * @param roles         associated roles.
	 */
	public User(long id, String forename, String lastname, String role, String username,
			List<Organization> organizations, List<Role> roles) {
		this.id = id;
		this.forename = forename;
		this.lastname = lastname;
		this.role = role;
		this.username = username;
		this.organizations = organizations;
		this.roles = roles;
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