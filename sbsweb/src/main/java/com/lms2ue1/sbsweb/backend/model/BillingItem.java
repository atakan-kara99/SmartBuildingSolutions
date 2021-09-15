package com.lms2ue1.sbsweb.backend.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private double quantities;
    private String unit;
    private double unitPrice;
    private String qtySplit;
    private String shortDesLinkedIFC;

    // ---- Associations ----//
    @ManyToOne // (cascade = { CascadeType.ALL }) tried w/ cascading
    @JoinColumn(name = "billing_unit_id")
    private BillingUnit billingUnit;
    @Size(min = 2)
    @ManyToMany(mappedBy = "billingItems")
    private List<Role> roles;
    @OneToMany
    @JoinColumn(name = "sub_billing_item")
    private List<BillingItem> billingItems;
    @ManyToOne
    private Status status;

    // ----------------------------------//
    // ---------- Constructors ----------//
    // ----------------------------------//
    public BillingItem() {
    }

    /**
     * Initializes a billing item. Only the parameters of the constructor are
     * columns (plus the FKs).
     * 
     * @param name         = name of the user
     * @param price        = price
     * @param sDesc        = short description
     * @param status       = status
     * @param quantities   = quantities
     * @param unit         = unit
     * @param uPrice       = unit price
     * @param qSplit       = qty split
     * @param sDLIFC       = short deslinked ifc
     * @param billUnit     = billing unit
     * @param billingItems = billing items
     */
    public BillingItem(String name, double price, String sDesc, Status status, double quantities, String unit,
	    double uPrice, String qSplit, String sDLIFC, BillingUnit billUnit, List<BillingItem> billingItems) {
	this.name = name;
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
    protected void setId(long id) {
	this.id = id;
    }

    protected void setPrice(double p) {
	this.price = p;
    }

    protected void setShortDescription(String sDesc) {
	this.shortDescription = sDesc;
    }

    protected void setStatus(Status s) {
	this.status = s;
    }

    protected void setQuantities(double qs) {
	this.quantities = qs;
    }

    protected void setUnit(String u) {
	this.unit = u;
    }

    protected void setUnitPrice(double uP) {
	this.unitPrice = uP;
    }

    protected void setQtySplit(String qtySplit) {
	this.qtySplit = qtySplit;
    }

    protected void setShortDesLinkedIFC(String shortDesLinkedIFC) {
	this.shortDesLinkedIFC = shortDesLinkedIFC;
    }

    protected void setBillingUnit(BillingUnit bu) {
	this.billingUnit = bu;
    }

    protected void setBillingItems(List<BillingItem> bis) {
	this.billingItems = bis;
    }

    protected void setRoles(List<Role> rs) {
	this.roles = rs;
    }

    // ----------------------------//
    // ---------- Misc ------------//
    // ----------------------------//

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof BillingItem) {
	    BillingItem tmpBillItem = (BillingItem) obj;
	    return tmpBillItem.getId() == this.id;
	}
	return false;
    }
}
