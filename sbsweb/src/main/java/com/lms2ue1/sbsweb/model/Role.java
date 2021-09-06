package com.lms2ue1.sbsweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotEmpty
	private String name;

	// ---- Associations ----//
	// TODO: set the associations
	private List<Project> projects;
	private List<Organization> organizations;
	private List<User> users;
	private List<Contract> contracts;
	private List<BillingItem> billingItems;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Role() {
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
	public void setId(long rId) {
		this.id = rId;
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
