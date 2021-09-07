package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.List;

/**
 * Class of organizations. Each organization is associated to one or more
 * contracts, two or more users, one or more projects and two or more roles.
 * 
 * @author juliusdaum
 *
 */
@Entity
public class Organisation {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable=false)
	private long id;
	@NotEmpty
	private String name;

	// ---- Associations ----//
	@Size(min = 1)
	@OneToMany(mappedBy = "organisation")
	private List<Project> projects;
	@Size(min = 2)
	@ManyToMany
	private List<User> users;
	@Size(min = 2)
	@OneToMany(mappedBy = "organisation")
	private List<Role> roles;
	@Size(min = 1)
	@ManyToMany
	private List<Contract> contracts;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	
	// TODO: Do we really want to allow this? Good for testing. (nka)
	public Organisation() {
	}

	/**
	 * Constructor to insert the data of the rest api json request.
	 * 
	 * @param id id of the organisation.
	 * @param n  name of the organisation.
	 * @param ps associated projects.
	 * @param us associated users.
	 * @param rs associated roles.
	 * @param cs assocaited contracts.
	 */
	public Organisation(long id, String n, List<Project> ps, List<User> us, List<Role> rs, List<Contract> cs) {
		this.id = id;
		this.name = n;
		this.projects = ps;
		this.users = us;
		this.roles = rs;
		this.contracts = cs;
	}

	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//
	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public List<Contract> getContracts() {
		return this.contracts;
	}

	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String n) {
		this.name = n;
	}

	public void setProjects(List<Project> p) {
		this.projects = p;
	}

	public void setUsers(List<User> u) {
		this.users = u;
	}

	public void setRoles(List<Role> r) {
		this.roles = r;
	}

	public void setContracts(List<Contract> c) {
		this.contracts = c;
	}
}
