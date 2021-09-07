package com.lms2ue1.sbsweb.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 * Contract of each organization. Each contract is associated to one or more
 * billing units, one project, two or more roles and two organizations.
 * 
 * @author juliusdaum
 *
 */
@Entity
public class Contract {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private long id;
	private String name;
	private String description;
	private Status status;
	private String consignee;
	private String contractor;

	// ---- Associations ----//
	@Size(min = 2, max = 2)
	@ManyToMany
	private List<Organisation> organisations;
	@Size(min = 2)
	@ManyToMany
	private List<Role> roles;
	@ManyToOne
	private Project project;
	@Size(min = 1)
	@OneToMany(mappedBy = "contract", orphanRemoval = true)
	private List<BillingUnit> billingUnits;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Contract() {
	}

	/**
	 * Initializes a contract object.
	 * 
	 * @param name          = name
	 * @param description   = description
	 * @param status        = status
	 * @param consignee     = consignee
	 * @param contructor    = contructor
	 * @param organisations = organisations
	 * @param projects      = projects
	 * @param billunits     = billing units
	 */
	public Contract(String name, String description, Status status, String consignee, String contructor,
			List<Organisation> organisations, Project projects) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.consignee = consignee;
		this.contractor = contructor;
		this.organisations = organisations;
		this.project = projects;
	}

	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//
	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Status getStatus() {
		return this.status;
	}

	public String getConsignee() {
		return this.consignee;
	}

	public String getContractor() {
		return this.contractor;
	}

	public List<Organisation> getOrganisations() {
		return this.organisations;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public Project getProject() {
		return this.project;
	}

	public List<BillingUnit> getBillingUnits() {
		return this.billingUnits;
	}

	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String n) {
		this.name = n;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public void setStatus(Status s) {
		this.status = s;
	}

	public void setConsignee(String cnsgn) {
		this.consignee = cnsgn;
	}

	public void setContractor(String cntrctr) {
		this.contractor = cntrctr;
	}

	public void setOrganizations(List<Organisation> os) {
		this.organisations = os;
	}

	public void setRoles(List<Role> rs) {
		this.roles = rs;
	}

	public void setProject(Project p) {
		this.project = p;
	}

	public void setBillingUnit(List<BillingUnit> bus) {
		this.billingUnits = bus;
	}

}
