package com.lms2ue1.sbsweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.List;

/**
 * Class of organizations. Each organization is associated to one or more
 * contracts, two or more users, one or more projects and two or more roles.
 * 
 * @author juliusdaum
 *
 */
@Entity
@Table(name = "organizations") // declare the name of the table
public class Organization {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;

	// ---- Associations ----//
	// TODO: set the associations
	private List<Project> projects;
	private List<User> users;
	private List<Role> roles;
	private List<Contract> contracts;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Organization() {
	}

	/**
	 * Initializes an organization object.
	 * 
	 * @param orgId
	 * @param orgName
	 */
	public Organization(long orgId, String orgName, List<Contract> contracts, List<User> users, List<Role> roles,
			List<Project> projects) {
		this.id = orgId;
		this.name = orgName;
		this.contracts = contracts;
		this.users = users;
		this.roles = roles;
		this.projects = projects;

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
	public void setId(long orgId) {
		this.id = orgId;
	}

	public void setName(String orgName) {
		this.name = orgName;
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
