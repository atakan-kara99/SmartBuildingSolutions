package com.lms2ue1.sbsweb.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="billing_item")
public class BillingItem {
	// ---- Attributes ----//
	@Id
	@Column(updatable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	// TODO: set the associations
	private long id;
	private double price;
	@Column(name="short_description")
	private String shortDescription;
	private String status;
	private double quantities;
	private String unit;
	@Column(name="unit_price")
	private double unitPrice;
	@Column(name="qty_split")
	private String qtySplit;
	@Column(name="short_desclinked_ifc")
	private String shortDesLinkedIFC;

	// ---- Associations ----//
	private BillingUnit billingUnit;
	private List<BillingItem> billingItems;
	private List<Role> roles;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	public BillingItem() {
	}

	// ----------------------------//
	// ---------- Getter ----------//
	// ----------------------------//
	public long getId() {
		return this.id;
	}

	public double getPrice() {
		return this.price;
	}

	public String getShortDescription() {
		return this.shortDescription;
	}

	public String getStatus() {
		return this.status;
	}

	public double getQuantities() {
		return this.quantities;
	}

	public String getUnit() {
		return this.unit;
	}

	public double getUnitPrice() {
		return this.unitPrice;
	}

	public String getQtySplit() {
		return this.qtySplit;
	}

	public String getShortDesLinkedIFC() {
		return this.shortDesLinkedIFC;
	}

	public BillingUnit getBillingUnit() {
		return this.billingUnit;
	}

	public List<BillingItem> getBillingItems() {
		return this.billingItems;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	// ----------------------------//
	// ---------- Setter ----------//
	// ----------------------------//
	public void setId(long id) {
		this.id = id;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setQuantities(double quantities) {
		this.quantities = quantities;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setQtySplit(String qtySplit) {
		this.qtySplit = qtySplit;
	}

	public void setShortDesLinkedIFC(String shortDesLinkedIFC) {
		this.shortDesLinkedIFC = shortDesLinkedIFC;
	}

	public void setBillingUnit(BillingUnit billingUnit) {
		this.billingUnit = billingUnit;
	}

	public void setBillingItems(List<BillingItem> billingItems) {
		this.billingItems = billingItems;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
