package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "ROLE")
public class Role {
	// A few adaptations to make the data model actually work (nka).

	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, unique = true)
	private long id;
	@NotEmpty
	private String name;
	private boolean manageUser;



	// ---- Associations ----//
	@ManyToMany
	@JoinTable(name = "PROJECT_ROLES", joinColumns = { @JoinColumn(name = "ROLES_ID") }, inverseJoinColumns = {
	            @JoinColumn(name = "PROJECTS_ID") })
	private List<Project> projects;
	@ManyToOne
	@JoinColumn(name = "organisation_id")
	private Organisation organisation;
	@OneToMany(mappedBy = "role")
	private List<User> users;
	@ManyToMany
	@JoinTable(name = "CONTRACT_ROLES", joinColumns = { @JoinColumn(name = "ROLES_ID") }, inverseJoinColumns = {
	            @JoinColumn(name = "CONTRACT_ID") })
	private List<Contract> contracts;
	@ManyToMany
	@JoinTable(name = "BILLING_ITEM_ROLES", joinColumns = { @JoinColumn(name = "ROLES_ID") }, inverseJoinColumns = {
	            @JoinColumn(name = "BILLING_ITEM_ID") })
	private List<BillingItem> billingItems;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Role() {
	}

	/**
	 * Initializes a role object.
	 * 
	 * @param name         the name of a role.
	 * @param projects     the associated projects.
	 * @param contracts    the associated contracts.
	 * @param billingItems the associated billing items.
	 * @param organisation the associated organisation.
	 * @param manageUser   whether the role is allowed to manage user or not.
	 */
	public Role(String name, List<Project> projects, List<Contract> contracts, List<BillingItem> billingItems,
			Organisation organisation, boolean manageUser) {
		this.name = name;
		this.projects = projects;
		this.contracts = contracts;
		this.billingItems = billingItems;
		this.organisation = organisation;
		this.manageUser = manageUser;
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

	public boolean isManageUser() {
		return manageUser;
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

	public void setManageUser(boolean manageUser) {
		this.manageUser = manageUser;
	}
}
