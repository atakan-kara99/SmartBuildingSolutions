package com.lms2ue1.sbsweb.backend.model;

import java.util.List;

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
import javax.validation.constraints.Size;

@Entity
public class Contract {
<<<<<<< HEAD
	
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, unique = true)
	private long id;
	@Column(unique = true)
	private String name;
	private String description;
	private String consignee;
	private String contractor;

	// ---- Associations ----//
	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;
	@Size(min = 2, max = 2)
	@ManyToMany
	@JoinTable(name = "CONTRACT_ORGANISATIONS", joinColumns = { @JoinColumn(name = "CONTRACT_ID") }, inverseJoinColumns = {
	            @JoinColumn(name = "ORGANISATIONS_ID") })
	private List<Organisation> organisations;
	@Size(min = 2)
	@ManyToMany(mappedBy = "contracts")
	private List<Role> roles;
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	@Size(min = 1)
	@OneToMany(mappedBy = "contract", orphanRemoval = true)
	private List<BillingUnit> billingUnits;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	// TODO: Do we actually want to allow this?
	public Contract() {
	}

	/**
	 * Initializes a contract object.
	 * Only the parameters of the constructor are columns (plus the FKs).
	 * 
	 * @param name          = name
	 * @param description   = description
	 * @param status        = status
	 * @param consignee     = consignee
	 * @param contructor    = contructor
	 * @param organisations = organisations
	 * @param project       = projects
	 * @param billunits     = billing units
	 */
	public Contract(String name, String description, Status status, String consignee, String contructor,
			List<Organisation> organisations, Project project) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.consignee = consignee;
		this.contractor = contructor;
		this.organisations = organisations;
		this.project = project;
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
=======
    // A few adaptations to make the data model actually work (nka).

    // ---- Attributes ----//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    private long id;
    @Column(unique = true)
    private String name;
    private String description;
    private Status status;
    private String consignee;
    private String contractor;

    // ---- Associations ----//
    @Size(min = 2, max = 2)
    @ManyToMany
    @JoinTable(name = "CONTRACT_ORGANISATIONS", joinColumns = {
	    @JoinColumn(name = "CONTRACT_ID") }, inverseJoinColumns = { @JoinColumn(name = "ORGANISATIONS_ID") })
    private List<Organisation> organisations;
    @Size(min = 2)
    @ManyToMany(mappedBy = "contracts")
    private List<Role> roles;
    @ManyToOne // (cascade = { CascadeType.ALL }) try to remove
    @JoinColumn(name = "project_id")
    private Project project;
    @Size(min = 1)
    @OneToMany(mappedBy = "contract", orphanRemoval = true)
    private List<BillingUnit> billingUnits;

    // ----------------------------------//
    // ---------- Constructors ----------//
    // ----------------------------------//
    // TODO: Do we actually want to allow this?
    public Contract() {
    }

    /**
     * Initializes a contract object. Only the parameters of the constructor are
     * columns (plus the FKs).
     * 
     * @param name          = name
     * @param description   = description
     * @param status        = status
     * @param consignee     = consignee
     * @param contructor    = contructor
     * @param organisations = organisations
     * @param project       = projects
     * @param billunits     = billing units
     */
    public Contract(String name, String description, Status status, String consignee, String contructor,
	    List<Organisation> organisations, Project project) {
	this.name = name;
	this.description = description;
	this.status = status;
	this.consignee = consignee;
	this.contractor = contructor;
	this.organisations = organisations;
	this.project = project;
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
    protected void setId(Long id) {
	this.id = id;
    }

    protected void setName(String n) {
	this.name = n;
    }

    protected void setDescription(String desc) {
	this.description = desc;
    }

    protected void setStatus(Status s) {
	this.status = s;
    }

    protected void setConsignee(String cnsgn) {
	this.consignee = cnsgn;
    }

    protected void setContractor(String cntrctr) {
	this.contractor = cntrctr;
    }

    protected void setOrganizations(List<Organisation> os) {
	this.organisations = os;
    }

    protected void setRoles(List<Role> rs) {
	this.roles = rs;
    }

    protected void setProject(Project p) {
	this.project = p;
    }

    protected void setBillingUnit(List<BillingUnit> bus) {
	this.billingUnits = bus;
    }

    // ----------------------------//
    // ---------- Misc ------------//
    // ----------------------------//

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Contract) {
	    Contract tmpContract = (Contract) obj;
	    return tmpContract.getId() == this.id;
	}
	return false;
    }
>>>>>>> web-dev

}
