package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
	@Column(updatable=false)
	private long id;
	private String street;
	private int houseNumber;
	private int zipCode;
	private String city;
	private String country;

	// ---- Associations ----//
	@OneToOne
	private Project project;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public Address() {
	}

	/**
	 * Constructor to insert the data of the rest api json request.
	 * 
	 * @param id    id of the address.
	 * @param st    name of the street.
	 * @param hNum  house number.
	 * @param zC    zip code of the city.
	 * @param c     city.
	 * @param cntry country.
	 * @param p     the associated project.
	 */
	public Address(long id, String st, int hNum, int zC, String c, String cntry, Project p) {
		this.id = id;
		this.street = st;
		this.houseNumber = hNum;
		this.zipCode = zC;
		this.city = c;
		this.country = cntry;
		this.project = p;
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
