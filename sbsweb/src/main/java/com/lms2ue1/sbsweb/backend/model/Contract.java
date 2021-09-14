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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
public class Contract {

    // A few adaptations to make the data model actually work (nka).

    // ---- Attributes ----//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    private long id;

    /*
     * @JsonProperty("id") private long adessoID;
     */
    @JsonProperty("name")
    @Column(unique = true)
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("consignee")
    private String consignee;
    @JsonProperty("contractor")
    private String contractor;
    @JsonProperty("status")
    private String adessoStatus;
    @JsonProperty("projectId")
    private long adessoProjectId;

    // ---- Associations ----//
    @JsonUnwrapped
    @Size(min = 2, max = 2)
    @ManyToMany
    @JoinTable(name = "CONTRACT_ORGANISATIONS", joinColumns = {
	    @JoinColumn(name = "CONTRACT_ID") }, inverseJoinColumns = { @JoinColumn(name = "ORGANISATIONS_ID") })
    private List<Organisation> organisations;
    @JsonUnwrapped
    @Size(min = 2)
    @ManyToMany(mappedBy = "contracts")
    private List<Role> roles;
    @JsonUnwrapped
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @JsonUnwrapped
    @Size(min = 1)
    @OneToMany(mappedBy = "contract", orphanRemoval = true)
    private List<BillingUnit> billingUnits;
    @ManyToOne
    private Status statusObj;

    // ----------------------------------//
    // ---------- Constructors ----------//
    // ----------------------------------//
    public Contract() {
    }

    /**
     * Initializes a contract object. Only the parameters of the constructor are
     * columns (plus the FKs).
     * 
     * @param name          = name
     * @param description   = description
     * @param consignee     = consignee
     * @param contructor    = contructor
     * @param organisations = organisations
     * @param project       = projects
     * @param billunits     = billing units
     */
    public Contract(String name, String description, String consignee, String contructor,
	    List<Organisation> organisations, Project project) {
	this.name = name;
	this.description = description;
	this.consignee = consignee;
	this.contractor = contructor;
	this.organisations = organisations;
	this.project = project;
    }

    public Contract(long id, String name, String description, String consignee, String contractor, String adessoStatus,
	    long projectId) {
	super();
	this.id = id;
	this.name = name;
	this.description = description;
	this.consignee = consignee;
	this.contractor = contractor;
	this.adessoStatus = adessoStatus;
	this.adessoProjectId = projectId;
    }

    // ----------------------------//
    // ---------- Getter ----------//
    // ----------------------------//
    public long getAdessoProjectId() {
	return adessoProjectId;
    }

    public Status getStatusObj() {
	return statusObj;
    }

    public Long getId() {
	return this.id;
    }

    public String getName() {
	return this.name;
    }

    public String getDescription() {
	return this.description;
    }

    public String getAdessoStatus() {
        return adessoStatus;
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
    public void setAdessoStatus(String adessoStatus) {
        this.adessoStatus = adessoStatus;
    }
    
    public void setAdessoProjectId(long projectId) {
	this.adessoProjectId = projectId;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setName(String n) {
	this.name = n;
    }

    public void setDescription(String desc) {
	this.description = desc;
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

    public void setStatusObj(Status statusObj) {
	this.statusObj = statusObj;
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

}
