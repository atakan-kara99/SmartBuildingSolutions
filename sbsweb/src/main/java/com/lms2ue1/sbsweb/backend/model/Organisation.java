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
	// A few adaptations to make the data model actually work (nka).

	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, unique = true)
	private long id;
	@NotEmpty
	@Column(unique = true)
	private String name;

	// ---- Associations ----//
	@Size(min = 1)
	@OneToMany(mappedBy = "organisation")
	private List<Project> projects;
	@Size(min = 2)
	@OneToMany
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
	 * @param name  = name of the organisation.
	 */
	public Organisation(String name) {
		this.name = name;
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

	public void setRoles(List<Role> r) {
		this.roles = r;
	}

	public void setContracts(List<Contract> c) {
		this.contracts = c;
	}
}
