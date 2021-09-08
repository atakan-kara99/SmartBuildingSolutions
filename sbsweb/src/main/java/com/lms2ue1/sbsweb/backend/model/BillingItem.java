package com.lms2ue1.sbsweb.backend.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 * Billing Item of a project. Associated to two or more roles, one billing unit
 * and two itself.
 * 
 * @author juliusdaum
 *
 */
@Entity
public class BillingItem {
	// ---- Attributes ----//
	@Id
	@Column(updatable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private double price;
	@Column(unique = true)
	private String name;
	private String shortDescription;
	private Status status;
	private double quantities;
	private String unit;
	private double unitPrice;
	private String qtySplit;
	private String shortDesLinkedIFC;

	// ---- Associations ----//
	@ManyToOne(cascade = {CascadeType.ALL})
	private BillingUnit billingUnit;
	@Size(min = 2)
	@ManyToMany
	private List<Role> roles;
	@OneToMany
	@JoinColumn(name = "sub_billing_item")
	private List<BillingItem> billingItems;

	// ----------------------------------//
	// ---------- Constructors ----------//
	// ----------------------------------//
	// TODO: Do we really want to allow this? Good for testing. (nka)
	public BillingItem() {
	}

	/**
	 * Initializes a billing item.
	 * 
	 * @param price  = price
	 * @param sDesc  = short description
	 * @param status = status
	 * @param quantities  = quantities
	 * @param unit 	 = unit
	 * @param uPrice = unit price
	 * @param qSplit = qty split
	 * @param sDLIFC = short deslinked ifc
	 * @param billUnit = billing unit
	 * @param billingItems = billing items
	 */
	public BillingItem(double price, String sDesc, Status status, double quantities, String unit, double uPrice, String qSplit,
			String sDLIFC, BillingUnit billUnit, List<BillingItem> billingItems) {
		this.price = price;
		this.shortDescription = sDesc;
		this.status = status;
		this.quantities = quantities;
		this.unit = unit;
		this.unitPrice = uPrice;
		this.qtySplit = qSplit;
		this.shortDesLinkedIFC = sDLIFC;
		this.billingUnit = billUnit;
		this.billingItems = billingItems;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Status getStatus() {
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

	public void setPrice(double p) {
		this.price = p;
	}

	public void setShortDescription(String sDesc) {
		this.shortDescription = sDesc;
	}

	public void setStatus(Status s) {
		this.status = s;
	}

	public void setQuantities(double qs) {
		this.quantities = qs;
	}

	public void setUnit(String u) {
		this.unit = u;
	}

	public void setUnitPrice(double uP) {
		this.unitPrice = uP;
	}

	public void setQtySplit(String qtySplit) {
		this.qtySplit = qtySplit;
	}

	public void setShortDesLinkedIFC(String shortDesLinkedIFC) {
		this.shortDesLinkedIFC = shortDesLinkedIFC;
	}

	public void setBillingUnit(BillingUnit bu) {
		this.billingUnit = bu;
	}

	public void setBillingItems(List<BillingItem> bis) {
		this.billingItems = bis;
	}

	public void setRoles(List<Role> rs) {
		this.roles = rs;
	}

}
