package com.lms2ue1.sbsweb.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BillingUnit {
    // ---- Attributes ----//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    private long id;
    private String shortDescription;
    private String longDescription;
    private String unit;
    private String completionDate;
    private String ownContractDefined;
    private double totalQuantity;
    private double totalPrice;

    // ---- Associations ----//
    /* Got problems with merging with contracts */
    @JsonIgnore
    @ManyToOne // (cascade = { CascadeType.MERGE }) // try to run w/
    @JoinColumn(name = "contract_id")
    private Contract contract;
    @Size(min = 1)
    @OneToMany(mappedBy = "billingUnit", orphanRemoval = true)
    private List<BillingItem> billingItems;

    // ----------------------------------//
    // ---------- Constructors ----------//
    // ----------------------------------//
    // TODO: Do we actually want to allow this?
    public BillingUnit() {
    }

    /**
     * Initializes a billing unit object. Only the parameters of the constructor are
     * columns (plus the FKs).
     * 
     * @param sDesc              = short description
     * @param lDesc              = long description
     * @param unit               = unit
     * @param completionDate     = completion date
     * @param ownContractDefined = own contract defined
     * @param totalQuantity      = total quantity
     * @param totalPrice         = total price
     * @param contract           = contract
     */
    public BillingUnit(String sDesc, String lDesc, String unit, String completionDate, String ownContractDefined,
	    double totalQuantity, double totalPrice, Contract contract) {
	this.shortDescription = sDesc;
	this.longDescription = lDesc;
	this.unit = unit;
	this.completionDate = completionDate;
	this.ownContractDefined = ownContractDefined;
	this.totalQuantity = totalQuantity;
	this.totalPrice = totalPrice;
	this.contract = contract;
    }

    // ----------------------------//
    // ---------- Getter ----------//
    // ----------------------------//
    public long getId() {
	return this.id;
    }

    public String getShortDescription() {
	return this.shortDescription;
    }

    public String getLongDescription() {
	return this.longDescription;
    }

    public String getUnit() {
	return this.unit;
    }

    public String getCompletionDate() {
	return this.completionDate;
    }

    public String getownContractDefined() {
	return this.ownContractDefined;
    }

    public double getTotalQuantity() {
	return this.totalQuantity;
    }

    public double getTotalPrice() {
	return this.totalPrice;
    }

    public Contract getContract() {
	return this.contract;
    }

    public List<BillingItem> getBillingItems() {
	return this.billingItems;
    }

    // ----------------------------//
    // ---------- Setter ----------//
    // ----------------------------//
    protected void setId(long id) {
	this.id = id;
    }

    protected void setShortDescription(String sDesc) {
	this.shortDescription = sDesc;
    }

    protected void setLongDescription(String lDesc) {
	this.longDescription = lDesc;
    }

    protected void setUnit(String u) {
	this.unit = u;
    }

    protected void setCompletionDate(String cD) {
	this.completionDate = cD;
    }

    protected void setownContractDefined(String oCD) {
	this.ownContractDefined = oCD;
    }

    protected void setTotalQuantity(double tQ) {
	this.totalQuantity = tQ;
    }

    protected void setTotalPrice(double tP) {
	this.totalPrice = tP;
    }

    protected void setContract(Contract c) {
	this.contract = c;
    }

    protected void setBillingItems(List<BillingItem> bis) {
	this.billingItems = bis;
    }

    // ----------------------------//
    // ---------- Misc ------------//
    // ----------------------------//

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof BillingUnit) {
	    BillingUnit tmpBillUnit = (BillingUnit) obj;
	    return tmpBillUnit.getId() == this.id;
	}
	return false;
    }
}
