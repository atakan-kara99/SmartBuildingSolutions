package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private long id;
	@NotEmpty
	private String name;

	// ---- Associations ----//
	@ManyToMany
	private List<Project> projects;
	@ManyToOne
	private Organisation organisation;
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

	/**
	 * Initializes a role object.
	 * 
	 * @param id           the unique id of a role.
	 * @param name         the name of a role.
	 * @param projects     the associated projects.
	 * @param organisation the associated organisation.
	 * @param contracts    the associated contracts.
	 * @param billingItems the associated billing items.
	 */
	public Role(long id, String name, List<Project> projects, Organisation organisation,
			List<Contract> contracts, List<BillingItem> billingItems) {
		this.id = id;
		this.name = name;
		this.projects = projects;
		this.organisation = organisation;
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
	
	// ----------------------------//
	// ---------- Misc ------------//
	// ----------------------------//

	@Override
	public String toString() {
		String str = "" + this;
		return str;
	}
}
