package com.lms2ue1.sbsweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

	// ------ Attributes ------//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	@Column(name="creation_date")
	private String creationDate;
	@Column(name="completion_date")
	private String completionDate;
	private String status;
	@Column(name="overall_costs")
	private double overallCosts;
	private String creator;
	// -- These attributes are not important for our use. --//
	private String image;
	@Column(name="image_type")
	private String imageType;
	@Column(name="image_file_name")
	private String imageFileName;

	// ------ Associations ------//
	@OneToMany(mappedBy = "project")
	private List<Address> address;
//	@OneToMany(mappedBy = "projects", orphanRemoval = true)
//	private List<Contract> contracts;
//	TODO: fix the references!
//	@ManyToOne
//	private List<Organization> organizations;
	@ManyToMany
	private List<Role> roles;

	// ----------------------------------//
	// ---------- Constructors ----------//
	public Project() {
	}

	/**
	 * Constructs the object, that the frontend needs.
	 * 
	 * @param projectId             the unique id of the project.
	 * @param name                  name of the project.
	 * @param description           a description of the project.
	 * @param creationDate          date where the project starts.
	 * @param completionDate        date where the project will be finished.
	 * @param status                the current status of the project.
	 * @param overallCosts          the sum of all costs.
	 * @param address               the fixed address of the project.
	 * @param ownerGroupIdentifiers the associated organizations to the project.
	 * @param roles                 the associated roles to the project.
	 * @param contracts             the associated contracts to the project.
	 */
	public Project(long projectId, String name, String description, String creationDate, String completionDate,
			String status, double overallCosts, Address address, List<Organization> organizations,
			List<Role> roles, List<Contract> contracts) {
		this.id = projectId;
		this.name = name;
		this.description = description;
		this.creationDate = creationDate;
		this.completionDate = completionDate;
		this.status = status;
		this.overallCosts = overallCosts;
		this.address = address;
		this.organizations = organizations;
		this.roles = roles;
		this.contracts = contracts;
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

	public String getStatus() {
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

	public List<Organization> getOrganizations() {
		return this.organizations;
	}

	public List<Role> getRoles() {
		return this.roles;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOverallCosts(double overallCosts) {
		this.overallCosts = overallCosts;
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

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public void setOrganizations(List<Organization> orgs) {
		this.organizations = orgs;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
