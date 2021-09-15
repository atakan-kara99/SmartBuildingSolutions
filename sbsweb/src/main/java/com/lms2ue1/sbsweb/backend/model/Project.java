package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

@Entity
public class Project {

	// ------ Attributes ------//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, unique = true)
	private long internID;
	
	@JsonProperty("id")
	private long adessoID;
	@JsonProperty("name")
	@Column(unique = true)
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("creationDate")
	private String creationDate;
	@JsonProperty("completionDate")
	private String completionDate;
	@JsonProperty("overallCost")
	private double overallCosts;
	@JsonProperty("creator")
	private String creator;
	// TODO: This will be the organisation.
	@JsonProperty("ownerGroupIdentifier")
	private String ownerGroupIdentifier;

	@JsonProperty("status")
	private String adessoStatus;
	// -- These attributes are not important for our use. --//
	@JsonProperty("image")
	private String image;
	@JsonProperty("imageType")
	private String imageType;
	@JsonProperty("imageFileName")
	private String imageFileName;
	
	// ------ Associations ------//
	/*@JsonUnwrapped
	@OneToOne
	@JoinColumn(name = "address_id")*/
	@Embedded
	private Address address;
	@JsonUnwrapped
	@Size(min = 1)
	@OneToMany(mappedBy = "project", orphanRemoval = true)
	private List<Contract> contracts;
	@JsonUnwrapped
	@ManyToOne
	@JoinColumn(name = "organisation_id")
	private Organisation organisation;
	@JsonUnwrapped
	@Size(min = 2)
	@ManyToMany(mappedBy = "projects")
	private List<Role> roles;
	@ManyToOne
	private Status statusObj;

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
	 * @param costs          overall costs.
	 * @param creator        name of the creator.
	 * @param img            path of the image.
	 * @param imgType        type of the image.
	 * @param imgFileName    name of the image.
	 * @param address        address of the project.
	 * @param organisation   associated organisation.
	 */
	public Project(String name, String desc, String creationDate, String completionDate, double costs,
			String creator, String img, String imgType, String imgFileName, Address address,
			Organisation organisation) {
		this.name = name;
		this.description = desc;
		this.creationDate = creationDate;
		this.completionDate = completionDate;
		this.overallCosts = costs;
		this.creator = creator;
		this.image = img;
		this.imageType = imgType;
		this.imageFileName = imgFileName;
		this.address = address;
		this.organisation = organisation;
	}
	
	public Project(long adessoID, String name, String description, String creationDate, String completionDate,
		double overallCosts, String creator, String ownerGroupIdentifier, String adessoStatus, String image,
		String imageType, String imageFileName) {
	    this.name = name;
	    this.description = description;
	    this.creationDate = creationDate;
	    this.completionDate = completionDate;
	    this.overallCosts = overallCosts;
	    this.creator = creator;
	    this.ownerGroupIdentifier = ownerGroupIdentifier;
	    this.adessoStatus = adessoStatus;
	    this.image = image;
	    this.imageType = imageType;
	    this.imageFileName = imageFileName;
	}
	
	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//
	public long getInternID() {
		return this.internID;
	}
	
	public long getAdessoID() {
	    return adessoID;
	}

	public String getOwnerGroupIdentifier() {
	    return ownerGroupIdentifier;
	}
	
	public String getAdessoStatus() {
	    return adessoStatus;
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

	public Status getStatusObj() {
		return this.statusObj;
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
	public void setInternID(long pId) {
		this.internID = pId;
	}
	
	public void setAdessoID(long adessoID) {
	    this.adessoID = adessoID;
	}
	
	public void setOwnerGroupIdentifier(String ownerGroupIdentifier) {
	    this.ownerGroupIdentifier = ownerGroupIdentifier;
	}
	
	public void setAdessoStatus(String adessoStatus) {
	    this.adessoStatus = adessoStatus;
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

	public void setStatusObj(Status s) {
		this.statusObj = s;
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
			return tmpProject.getInternID() == this.internID;
		}
		return false;
	}

}
