package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class Address {
    // ---- Attributes ----//
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    private long id;*/

    @JsonProperty("id")
    private long adessoAddressID;
    @JsonProperty("street")
    private String street;
    @JsonProperty("houseNumber")
    private int houseNumber;
    @JsonProperty("zipCode")
    private int zipCode;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;

    // ---- Associations ----//
    /*@JsonUnwrapped
    @OneToOne(mappedBy = "address")*/
    //private Project project;

    // ----------------------------------//
    // ---------- Constructors ----------//
    // ----------------------------------//
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
    public Address(long adessoID, String street, int houseNumber, int zip, String city, String country) {
	this.adessoAddressID = adessoID;
	this.street = street;
	this.houseNumber = houseNumber;
	this.zipCode = zip;
	this.city = city;
	this.country = country;
    }

    // ----------------------------//
    // ---------- Getter ----------//
    // ----------------------------//

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

    // ----------------------------//
    // ---------- Setter ----------//
    // ----------------------------//

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

    // ----------------------------//
    // ---------- Misc ------------//
    // ----------------------------//

    /*@Override
    public boolean equals(Object obj) {
	if (obj instanceof Address) {
	    Address tmpAddress = (Address) obj;
	    return tmpAddress.getId() == this.id;
	}
	return false;
    }*/

}
