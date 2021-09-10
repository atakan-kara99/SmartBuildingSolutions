package com.lms2ue1.sbsweb.backend.model;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	
	/* next statuses */
	private List<Status> nextStatuses = new Map<Status>();

	// ---- Associations ---- //
	@ManyToOne
	private Project project;
	@ManyToOne
	private Contract contract;
	@ManyToMany
	private Organisation organisation;
	@ManyToOne
	private BillingUnit billingUnit;
	@ManyToOne
	private BillingItem billingItem;
	
	
	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Status () {}
	
	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//
	public Long getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public Project getProject() {
		return this.project;
	}
	public Contract getContract() {
		return this.contract;
	}
	public Organisation getOrganisation() {
		return this.organisation;
	}
	public BillingUnit getBillingUnit() {
		return this.billingUnit;
	}
	public BillingItem getBillingItem() {
		return this.billingItem;
	}
	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
	
	

}
