package com.lms2ue1.sbsweb.backend.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
@Entity
public class Status {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String name;

	// ---- Associations ---- //
	@OneToMany(mappedBy = "status")
	private List<Project> projects;
	@OneToMany(mappedBy = "status")
	private List<Contract> contracts;
	@OneToMany(mappedBy = "status")
	private List<BillingUnit> billingUnits;
	@OneToMany(mappedBy = "status")
	private List<BillingItem> billingItems;

	@OneToOne
	@JoinColumn(name = "next_status")
	private Status nextStatus;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Status() {
	}
	/**
	 * 
	 * 
	 * @param name
	 * @param description
	 */
	public Status(String name, Status nextStatus) {
		this.name = name;
		this.nextStatus = nextStatus;
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

	public List<BillingUnit> getBillingUnit() {
		return this.billingUnits;
	}

	public List<BillingItem> getBillingItem() {
		return this.billingItems;
	}
	
	public Status getNextStatus() {
	    return nextStatus;
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

	public void setBillingUnit(List<BillingUnit> bus) {
		this.billingUnits = bus;
	}
	
	public void setNextStatus(Status nextStatus) {
	    this.nextStatus = nextStatus;
	}

	public void setBillingItem(List<BillingItem> bis) {
		this.billingItems = bis;
	}

}
