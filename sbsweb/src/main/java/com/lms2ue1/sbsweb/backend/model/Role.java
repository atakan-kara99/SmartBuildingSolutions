package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable=false)
	private long id;
	@NotEmpty
	private String name;

	// ---- Associations ----//
	@ManyToMany
	private List<Project> projects;
	@ManyToOne
	private Organisation organisation;
	@ManyToMany
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

	/**
	 * Initializes a role object.
	 * 
	 * @param id  the unique id of a role.
	 * @param n   the name of a role.
	 * @param ps  the associated projects.
	 * @param os  the associated organisations.
	 * @param us  the associated users.
	 * @param cs  the associated contracts.
	 * @param bus the associated billing items.
	 */
	public Role(long id, String n, List<Project> ps, Organisation os, List<User> us, List<Contract> cs,
			List<BillingItem> bus) {
		this.id = id;
		this.name = n;
		this.projects = ps;
		this.organisation = os;
		this.users = us;
		this.contracts = cs;
		this.billingItems = bus;
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

	public Organisation getOrganisation() {
		return this.organisation;
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

	public void setOrganisation(Organisation o) {
		this.organisation = o;
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
