package com.lms2ue1.sbsweb.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
     * The status of a project, contract or billing item
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

    public List<Project> getProject() {
	return this.projects;
    }

    public List<Contract> getContract() {
	return this.contracts;
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

    public void setProject(List<Project> ps) {
	this.projects = ps;
    }

    public void setContract(List<Contract> cs) {
	this.contracts = cs;
    }

    public void setNextStati(List<Status> nextStati) {
	this.nextStati = nextStati;
    }

    public void setBillingItem(List<BillingItem> bis) {
	this.billingItems = bis;
    }
    
    @Override
    public String toString() {
	return name;
    }
}
