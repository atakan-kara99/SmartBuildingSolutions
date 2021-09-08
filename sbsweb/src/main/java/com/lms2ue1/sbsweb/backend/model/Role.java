package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Custom class role. Roles are essential for the authorization.
 * 
 * @author juliusdaum
 *
 */
@Entity
public class Role {
	// A few adaptations to make the data model actually work (nka).

	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, unique = true)
	private long id;
	@NotEmpty
	private String name;

	// ---- Associations ----//
	@ManyToMany
	private List<Project> projects;
	@ManyToMany(cascade = CascadeType.MERGE)
	private List<Organisation> organisations;
	@OneToMany
	private List<User> users;
	@ManyToMany
	private List<Contract> contracts;
	@ManyToMany
	private List<BillingItem> billingItems;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Role() {
	}

	// TODO: Organisation raus und bei Rolle speichern?
	/**
	 * Initializes a role object.
	 * 
	 * @param name         the name of a role.
	 * @param projects     the associated projects.
	 * @param contracts    the associated contracts.
	 * @param billingItems the associated billing items.
	 */
	public Role(String name, List<Project> projects, List<Contract> contracts, List<BillingItem> billingItems) {
		this.name = name;
		this.projects = projects;
		this.contracts = contracts;
		this.billingItems = billingItems;
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

	public List<Organisation> getOrganisations() {
		return this.organisations;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public List<Contract> getContracts() {
		return this.contracts;
	}

	public List<BillingItem> getBillingItems() {
		return this.billingItems;
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

	public void setProjects(List<Project> ps) {
		this.projects = ps;
	}

	public void setOrganisation(List<Organisation> o) {
		this.organisations = o;
	}

	public void setUsers(List<User> us) {
		this.users = us;
	}

	public void setContracts(List<Contract> cs) {
		this.contracts = cs;
	}

	public void setBillingItems(List<BillingItem> bs) {
		this.billingItems = bs;
	}

}
