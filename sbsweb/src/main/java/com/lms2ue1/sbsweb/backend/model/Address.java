package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "addresses")
public class Address {
    // ---- Attributes ----//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    private long id;
    private String street;
    private int houseNumber;
    private int zipCode;
    private String city;
    private String country;

    // ---- Associations ----//
    @JsonIgnore
    @OneToOne(mappedBy = "address")
    //@JoinColumn(name = "project_id")
    private Project project;

    // ----------------------------------//
    // ---------- Constructors ----------//
    // ----------------------------------//
    // TODO: Do we really want to allow this? Good for testing. (nka)
    public Address() {
    }

    /**
     * Constructor to insert the data of the rest api json request. Only the
     * parameters of the constructor are columns (plus the FKs).
     * 
     * @param street      = name of the street.
     * @param houseNumber = house number.
     * @param zip         = zip code of the city.
     * @param city        = city.
     * @param country     = country.
     */
    public Address(String street, int houseNumber, int zip, String city, String country) {
	this.street = street;
	this.houseNumber = houseNumber;
	this.zipCode = zip;
	this.city = city;
	this.country = country;
    }

    // ----------------------------//
    // ---------- Getter ----------//
    // ----------------------------//
    public long getId() {
	return this.id;
    }

    public String getStreet() {
	return this.street;
    }

    public int getHouseNumber() {
	return this.houseNumber;
    }

    public int getZipCode() {
	return this.zipCode;
    }

    public String getCity() {
	return this.city;
    }

    public String getCountry() {
	return this.country;
    }

    public Project getProject() {
	return this.project;
    }

    // ----------------------------//
    // ---------- Setter ----------//
    // ----------------------------//
    protected void setAddressId(long aId) {
	this.id = aId;
    }

    protected void setStreet(String s) {
	this.street = s;
    }

    protected void setHouseNumber(int hNum) {
	this.houseNumber = hNum;
    }

    protected void setZipCode(int zCd) {
	this.zipCode = zCd;
    }

    protected void setCity(String c) {
	this.city = c;
    }

    protected void setCountry(String cntry) {
	this.country = cntry;
    }

    protected void setProject(Project p) {
	this.project = p;
    }

    // ----------------------------//
    // ---------- Misc ------------//
    // ----------------------------//

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Address) {
	    Address tmpAddress = (Address) obj;
	    return tmpAddress.getId() == this.id;
	}
	return false;
    }

}
