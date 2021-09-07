package com.lms2ue1.sbsweb.model;

import java.util.List;

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
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private double price;
	private String shortDescription;
	private String status;
	private double quantities;
	private String unit;
	private double unitPrice;
	private String qtySplit;
	private String shortDesLinkedIFC;

	// ---- Associations ----//
	@ManyToOne
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
	public BillingItem() {
	}

	/**
	 * Initializes a billing item.
	 * 
	 * @param id     = id
	 * @param p      = price
	 * @param sDesc  = short description
	 * @param s      = status
	 * @param qs     = quantities
	 * @param u      = unit
	 * @param uP     = unit price
	 * @param qS     = qty split
	 * @param sDLIFC = short deslinked ifc
	 * @param b      = billing unit
	 * @param rs     = roles
	 * @param bis    = billing items
	 */
	public BillingItem(long id, double p, String sDesc, String s, double qs, String u, double uP, String qS,
			String sDLIFC, BillingUnit b, List<Role> rs, List<BillingItem> bis) {
		this.id = id;
		this.price = p;
		this.shortDescription = sDesc;
		this.status = s;
		this.quantities = qs;
		this.unit = u;
		this.unitPrice = uP;
		this.qtySplit = qS;
		this.shortDesLinkedIFC = sDLIFC;
		this.billingUnit = b;
		this.roles = rs;
		this.billingItems = bis;
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

	public void setPrice(double p) {
		this.price = p;
	}

	public void setShortDescription(String sDesc) {
		this.shortDescription = sDesc;
	}

	public void setStatus(String s) {
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
