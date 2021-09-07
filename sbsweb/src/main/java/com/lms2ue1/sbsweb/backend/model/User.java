package com.lms2ue1.sbsweb.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * User will be created at the frontend side of the application.
 * 
 * @author juliusdaum
 */
@Entity
public class User {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private long id;
	@NotEmpty
	private String forename;
	@NotEmpty
	private String lastname;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;

	// ---- Associations ----//
	@Size(min = 1)
	@ManyToMany
	private List<Organisation> organisations;
	@Size(min = 2)
	@ManyToMany
	private List<Role> roles;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//

	// TODO: Do we really want to allow this? Good for testing. (nka)
	public User() {
	}

	/**
	 * Constructor for OrgAdmin. He can create users.
	 * 
	 * @param firstname     first name of the user.
	 * @param lastname      last name of the user.
	 * @param organisations at least one organisation the user works for.
	 * @param username      user name.
	 * @param password      password of the user (encrypted).
	 */
	public User(String firstname, String lastname, List<Organisation> organisations, String username,
			String password) {
		this.forename = firstname;
		this.lastname = lastname;
		this.organisations = organisations;
		this.username = username;
		this.password = password;
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

	public Role getRole() {
		return this.role;
	}

	public String getUsername() {
		return this.username;
	}

	public List<Organisation> getOrganisations() {
		return this.organisations;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public String getPassword() {
		return this.password;
	}

	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
	public void setId(Long id) {
		this.id = id;
	}

	public void setForename(String fname) {
		this.forename = fname;
	}

	public void setLastname(String lname) {
		this.lastname = lname;
	}

	public void setRole(Role r) {
		this.role = r;
	}

	public void setUsername(String uname) {
		this.username = uname;
	}

	public void setOrganisations(List<Organisation> os) {
		this.organisations = os;
	}

	public void setRoles(List<Role> rs) {
		this.roles = rs;
	}

	public void setPassword(String p) {
		this.password = p;
	}
}