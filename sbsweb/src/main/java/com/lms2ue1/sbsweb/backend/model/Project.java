package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import java.util.List;

@Entity
public class Project {
    // ------ Attributes ------//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    private long id;
    @Column(unique = true)
    private String name;
    private String description;
    private String creationDate;
    private String completionDate;
    private Status status;
    private double overallCosts;
    private String creator;
    // -- These attributes are not important for our use. --//
    private String image;
    private String imageType;
    private String imageFileName;

    // ------ Associations ------//
    // TODO: Joined nicht! Join überprüfen!
    @OneToOne // (cascade = {CascadeType.ALL}) try w/ cascading
    @JoinColumn(name = "address_id")
    private Address address;
    @Size(min = 1)
    @OneToMany(mappedBy = "project", orphanRemoval = true)
    private List<Contract> contracts;
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
    @Size(min = 2)
    @ManyToMany(mappedBy = "projects")
    private List<Role> roles;

    // ----------------------------------//
    // ---------- Constructors ----------//

    public Project() {
    }

    /**
     * Constructor to insert the data of the rest api json request. Only the
     * parameters of the constructor are columns (plus the FKs).
     * 
     * @param name           name of the project.
     * @param desc           description.
     * @param creationDate   date where the project started.
     * @param completionDate date where the project should be finished.
     * @param status         current status of the project.
     * @param costs          overall costs.
     * @param creator        name of the creator.
     * @param img            path of the image.
     * @param imgType        type of the image.
     * @param imgFileName    name of the image.
     * @param address        address of the project.
     * @param organisation   associated organisation.
     */
    public Project(String name, String desc, String creationDate, String completionDate, Status status, double costs,
	    String creator, String img, String imgType, String imgFileName, Address address,
	    Organisation organisation) {
	this.name = name;
	this.description = desc;
	this.creationDate = creationDate;
	this.completionDate = completionDate;
	this.status = status;
	this.overallCosts = costs;
	this.creator = creator;
	this.image = img;
	this.imageType = imgType;
	this.imageFileName = imgFileName;
	this.address = address;
	this.organisation = organisation;
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

    public String getDescription() {
	return this.description;
    }

    public String getCreationDate() {
	return this.creationDate;
    }

    public String getCompletionDate() {
	return this.completionDate;
    }

    public Status getStatus() {
	return this.status;
    }

    public double getOverallCosts() {
	return this.overallCosts;
    }

    public String getCreator() {
	return this.creator;
    }

    public String getImage() {
	return this.image;
    }

    public String getImageType() {
	return this.imageType;
    }

    public String getImageFileName() {
	return this.imageFileName;
    }

    public Address getAddress() {
	return this.address;
    }

    public List<Contract> getContracts() {
	return this.contracts;
    }

    public Organisation getOrganisation() {
	return this.organisation;
    }

    public List<Role> getRoles() {
	return roles;
    }

    // ----------------------------//
    // ---------- Setter ----------//
    // ----------------------------//
    public void setId(long pId) {
	this.id = pId;
    }

    public void setName(String n) {
	this.name = n;
    }

    public void setDescription(String desc) {
	this.description = desc;
    }

    public void setCreationDate(String creationDate) {
	this.creationDate = creationDate;
    }

    public void setCompletionDate(String completionDate) {
	this.completionDate = completionDate;
    }

    public void setStatus(Status s) {
	this.status = s;
    }

    public void setOverallCosts(double oC) {
	this.overallCosts = oC;
    }

    public void setImage(String img) {
	this.image = img;
    }

    public void setImageType(String imgType) {
	this.imageType = imgType;
    }

    public void setImageFileName(String imgFileName) {
	this.imageFileName = imgFileName;
    }

    public void setAddress(Address a) {
	this.address = a;
    }

    public void setContracts(List<Contract> cs) {
	this.contracts = cs;
    }

    public void setOrganisation(Organisation o) {
	this.organisation = o;
    }

    public void setRoles(List<Role> roles) {
	this.roles = roles;
    }

    public void setCreator(String creator) {
	this.creator = creator;
    }

    // ----------------------------//
    // ---------- Misc ------------//
    // ----------------------------//

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Project) {
	    Project tmpProject = (Project) obj;
	    return tmpProject.getId() == this.id;
	}
	return false;
    }

}
