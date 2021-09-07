package com.lms2ue1.sbsweb.model;

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
	@Column(updatable=false)
	private long id;
	private String name;
	private String description;
	private String status;
	private String consignee;
	private String contractor;

	// ---- Associations ----//
	@Size(min = 2)
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
	 * @param id      = id
	 * @param n       = name
	 * @param desc    = description
	 * @param s       = status
	 * @param cnsgn   = consignee
	 * @param cntrctr = contructor
	 * @param os      = organisations
	 * @param rs      = roles
	 * @param ps      = projects
	 * @param bus     = billing units
	 */
	public Contract(long id, String n, String desc, String s, String cnsgn, String cntrctr, List<Organisation> os,
			List<Role> rs, Project ps, List<BillingUnit> bus) {
		this.id = id;
		this.name = n;
		this.description = desc;
		this.status = s;
		this.consignee = cnsgn;
		this.contractor = cntrctr;
		this.organisations = os;
		this.roles = rs;
		this.project = ps;
		this.billingUnits = bus;
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

	public String getStatus() {
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

	public void setStatus(String s) {
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
