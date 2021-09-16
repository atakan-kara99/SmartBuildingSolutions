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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
public class BillingUnit {
    // ---- Attributes ----//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    private long internID;

    @JsonProperty("id")
    private String adessoID;
    @JsonProperty("shortDescription")
    private String shortDescription;
    @JsonProperty("longDescription")
    private String longDescription;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("completionDate")
    private String completionDate;
    @JsonProperty("ownContractDefined")
    private String ownContractDefined;
    @JsonProperty("totalQuantity")
    private double totalQuantity;
    @JsonProperty("totalPrice")
    private double totalPrice;

    // ---- Associations ----//
    /* Got problems with merging with contracts */
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
    @OneToMany(mappedBy = "billingUnit", orphanRemoval = true)
    @JsonProperty("billingItems")
    @JsonUnwrapped
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
    public long getInternId() {
	return this.internID;
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

    public String getAdessoID() {
	return adessoID;
    }

    // ----------------------------//
    // ---------- Setter ----------//
    // ----------------------------//
    public void setInternId(long id) {
	this.internID = id;
    }

    public void setAdessoID(String adessoID) {
	this.adessoID = adessoID;
    }

    public void setOwnContractDefined(String ownContractDefined) {
	this.ownContractDefined = ownContractDefined;
    }

    public void setShortDescription(String sDesc) {
	this.shortDescription = sDesc;
    }

    public void setLongDescription(String lDesc) {
	this.longDescription = lDesc;
    }

    public void setUnit(String u) {
	this.unit = u;
    }

    public void setCompletionDate(String cD) {
	this.completionDate = cD;
    }

    public void setTotalQuantity(double tQ) {
	this.totalQuantity = tQ;
    }

    public void setTotalPrice(double tP) {
	this.totalPrice = tP;
    }

    public void setContract(Contract c) {
	this.contract = c;
    }

    public void setBillingItems(List<BillingItem> bis) {
	this.billingItems = bis;
    }

    // ----------------------------//
    // ---------- Misc ------------//
    // ----------------------------//

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof BillingUnit) {
	    BillingUnit tmpBillUnit = (BillingUnit) obj;
	    return tmpBillUnit.getInternId() == this.internID;
	}
	return false;
    }
}
