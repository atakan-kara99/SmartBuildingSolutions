package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class Address {
    // ---- Attributes ----//
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

    public void setStreet(String s) {
	this.street = s;
    }

    public void setHouseNumber(int hNum) {
	this.houseNumber = hNum;
    }

    public void setZipCode(int zCd) {
	this.zipCode = zCd;
    }

    public void setCity(String c) {
	this.city = c;
    }

    public void setCountry(String cntry) {
	this.country = cntry;
    }
}
