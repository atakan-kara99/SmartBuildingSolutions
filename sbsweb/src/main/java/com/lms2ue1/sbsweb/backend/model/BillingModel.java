package com.lms2ue1.sbsweb.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class BillingModel {
    // ---- Attributes ----//
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("ownContractDefined")
    private String ownContractDefined;
    @JsonProperty("totalQuantity")
    private double totalQuantity;
    @JsonProperty("totalPrice")
    private double totalPrice;

    // ---- Associations ----//
    @JsonUnwrapped
    @JsonProperty("billingUnits")
    List<BillingUnit> billingUnits;

    // ----------------------------------//
    // ---------- Constructors ----------//
    // ----------------------------------//
    public BillingModel() {
    }

    public BillingModel(String currency, String ownContractDefined, double totalQuantity, double totalPrice,
	    List<BillingUnit> billingUnits) {
	super();
	this.currency = currency;
	this.ownContractDefined = ownContractDefined;
	this.totalQuantity = totalQuantity;
	this.totalPrice = totalPrice;
	this.billingUnits = billingUnits;
    }

    // ----------------------------//
    // ---------- Getter ----------//
    // ----------------------------//
    public String getCurrency() {
	return currency;
    }

    public String getOwnContractDefined() {
	return ownContractDefined;
    }

    public double getTotalQuantity() {
	return totalQuantity;
    }

    public double getTotalPrice() {
	return totalPrice;
    }

    public List<BillingUnit> getBillingUnits() {
	return billingUnits;
    }

    // ----------------------------//
    // ---------- Setter ----------//
    // ----------------------------//
    public void setCurrency(String currency) {
	this.currency = currency;
    }

    public void setOwnContractDefined(String ownContractDefined) {
	this.ownContractDefined = ownContractDefined;
    }

    public void setTotalQuantity(double totalQuantity) {
	this.totalQuantity = totalQuantity;
    }

    public void setTotalPrice(double totalPrice) {
	this.totalPrice = totalPrice;
    }

    public void setBillingUnits(List<BillingUnit> billingUnits) {
	this.billingUnits = billingUnits;
    }

}
