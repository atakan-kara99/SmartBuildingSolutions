package com.lms2ue1.sbsweb.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Contract of each organization. Each contract is associated to one or more
 * billing units, one project, two or more roles and two organizations.
 * 
 * @author juliusdaum
 *
 */
@Entity
@Table(name="contracts")
public class Contract {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	private String status;
	private String consignee;
	private String contractor;

	// ---- Associations ----//
	@ManyToMany
	private List<Organization> organizations;
	@ManyToMany
	private List<Role> roles;
	@ManyToOne
	private Project project;
	@OneToMany(mappedBy="contract", orphanRemoval=true)
	private List<BillingUnit> billingUnits;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Contract() {
	}

	/**
	 * Initializes a user object.
	 * 
	 * @param id            the unique id of the user.
	 * @param forename
	 * @param lastname
	 * @param role          the associated role.
	 * @param username
	 * @param organizations associated organizations.
	 * @param roles         associated roles.
	 */
	public Contract(long id, String name, String description, String status, String consignee, String contractor,
			List<Organization> organizations, List<Role> roles, Project projects, List<BillingUnit> billingUnits) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.consignee = consignee;
		this.contractor = contractor;
		this.organizations = organizations;
		this.roles = roles;
		this.project = projects;
		this.billingUnits = billingUnits;
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

	public List<Organization> getOrganizations() {
		return this.organizations;
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
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void setProject(Project p) {
		this.project = p;
	}
	public void setBillingUnit(List<BillingUnit> billingUnits) {
		this.billingUnits = billingUnits;
	}

}
