package com.lms2ue1.sbsweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import java.util.List;

@Entity
public class Role {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotEmpty
	private String name;

	// ---- Associations ----//
	@ManyToMany
	private List<Project> projects;
//	@ManyToOne
//	private List<Organization> organizations;
	@ManyToMany
	private List<User> users;
	@ManyToMany
	private List<Contract> contracts;
	@ManyToMany
	@Column(name="billing_items")
	private List<BillingItem> billingItems;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Role() {
	}

	/**
	 * Initializes a role object.
	 * 
	 * @param id            the unique id of a role.
	 * @param name          the name of a role.
	 * @param projects      the associated projects.
	 * @param organizations the associated organizations.
	 * @param users         the associated users.
	 * @param contracts     the associated contracts.
	 * @param billingItems  the associated billing items.
	 */
	public Role(long id, String name, List<Project> projects, List<Organization> organizations, List<User> users,
			List<Contract> contracts, List<BillingItem> billingItems) {
		this.id = id;
		this.name = name;
		this.projects = projects;
		this.organizations = organizations;
		this.users = users;
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

	public List<Organization> getOrganizations() {
		return this.organizations;
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

	public void setProjects(List<Project> p) {
		this.projects = p;
	}

	public void setOrganizations(List<Organization> o) {
		this.organizations = o;
	}

	public void setUsers(List<User> u) {
		this.users = u;
	}

	public void setContracts(List<Contract> c) {
		this.contracts = c;
	}

	public void setBillingItems(List<BillingItem> b) {
		this.billingItems = b;
	}

}
