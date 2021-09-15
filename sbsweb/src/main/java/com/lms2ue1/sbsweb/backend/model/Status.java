package com.lms2ue1.sbsweb.backend.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Status {
    // ---- Attributes ----//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String name;
    private String description;

    // ---- Associations ---- //
    @OneToMany(mappedBy = "status")
    private List<Project> projects;
    @OneToMany(mappedBy = "status")
    private List<Contract> contracts;
    @OneToMany(mappedBy = "status")
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
     * @param name
     * @param description
     */
    public Status(String name, String description, List<Status> nextStati) {
	this.name = name;
	this.description = description;
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

    public String getDescription() {
	return this.description;
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

    public void setDescription(String desc) {
	this.description = desc;
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

}
