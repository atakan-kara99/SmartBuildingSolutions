package com.lms2ue1.sbsweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Address of each project. Associated to one project.
 * 
 * @author juliusdaum
 */
@Entity
@Table(name = "addresses")
public class Address {
	// ---- Attributes ----//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String street;
	private int houseNumber;
	private int zipCode;
	private String city;
	private String country;

	// ---- Associations ----//
	@ManyToOne
	private Project project;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Address() {
	}

	/**
	 * Construction of address object that the frontend will use.
	 * 
	 * @param addressId   unique id of the object.
	 * @param street      the name of street.
	 * @param houseNumber houseNumber of the project.
	 * @param zipCode     unique zip code of the city.
	 * @param city        name of the city.
	 * @param country     name of the country.
	 * @param Project     the associated project to this address.
	 */
	public Address(long addressId, String street, int houseNumber, int zipCode, String city, String country,
			Project project) {
		this.id = addressId;
		this.street = street;
		this.houseNumber = houseNumber;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
		this.project = project;
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
	public void setAddressId(long aId) {
		this.id = aId;
	}

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

	public void setProject(Project p) {
		this.project = p;
	}

}
