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
	@OneToMany(mappedBy = "statusObj")
	private List<Project> projects;
	@OneToMany(mappedBy = "statusObj")
	private List<Contract> contracts;
	@OneToMany(mappedBy = "statusObj")
	private List<BillingItem> billingItems;

	@OneToMany
	@JoinColumn(name = "next_stati")
	private List<Status> nextStati;

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
	public Status(String name, List<Status> nextStati) {
		this.name = name;
		this.nextStati = nextStati;
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

	public List<BillingItem> getBillingItem() {
		return this.billingItems;
	}
	
	public List<Status> getNextStati() {
	    return nextStati;
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
	
	public void setNextStati(List<Status> nextStati) {
	    this.nextStati = nextStati;
	}

	public void setBillingItem(List<BillingItem> bis) {
		this.billingItems = bis;
	}

}
