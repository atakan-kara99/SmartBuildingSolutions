package com.lms2ue1.sbsweb.backend.model;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.mapping.Map;

@Entity
public class Status {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String name;
	private String description;

	// ---- Associations ---- //
	@OneToMany(mappedBy = "status")
	private List<Project> projects;
	@ManyToMany(mappedBy = "stati")
	private List<Organisation> organisations;
	@OneToMany(mappedBy = "status")
	private List<Contract> contracts;
	@OneToMany(mappedBy = "status")
	private List<BillingUnit> billingUnits;
	@OneToMany(mappedBy = "status")
	private List<BillingItem> billingItems;

	// TODO: try to implement HashMap<Role, Status>
	@OneToMany
	@JoinColumn(name = "next_stati")
	private Map<Role, Status> nextStatus = new HashMap<Role, Status>();

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Status() {
	}
	/**
	 * 
	 * @param name
	 * @param description
	 */
	public Status(String name, String description) {
		this.name = name;
		this.description = description;
	}

	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//
	protected Long getId() {
		return this.id;
	}

	protected String getName() {
		return this.name;
	}

	protected String getDescription() {
		return this.description;
	}

	protected List<Project> getProject() {
		return this.projects;
	}

	protected List<Contract> getContract() {
		return this.contracts;
	}

	protected List<Organisation> getOrganisation() {
		return this.organisations;
	}

	protected List<BillingUnit> getBillingUnit() {
		return this.billingUnits;
	}

	protected List<BillingItem> getBillingItem() {
		return this.billingItems;
	}

	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
	protected void setId(Long id) {
		this.id = id;
	}

	protected void setName(String n) {
		this.name = n;
	}

	protected void setDescription(String desc) {
		this.description = desc;
	}

	protected void setProject(List<Project> ps) {
		this.projects = ps;
	}

	protected void setContract(List<Contract> cs) {
		this.contracts = cs;
	}

	protected void setOrganisation(List<Organisation> os) {
		this.organisations = os;
	}

	protected void setBillingUnit(List<BillingUnit> bus) {
		this.billingUnits = bus;
	}

	public void setBillingItem(List<BillingItem> bis) {
		this.billingItems = bis;
	}

}
