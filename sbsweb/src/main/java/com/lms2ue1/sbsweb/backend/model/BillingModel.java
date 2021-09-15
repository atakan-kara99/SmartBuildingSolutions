package com.lms2ue1.sbsweb.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class BillingModel {
    
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("ownContractDefined")
    private String ownContractDefined;
    @JsonProperty("totalQuantity")
    private double totalQuantity;
    @JsonProperty("totalPrice")
    private double totalPrice;
    
    @JsonUnwrapped
    @JsonProperty("billingUnits")
    List<BillingUnit> billingUnits;

    public BillingModel(String currency, String ownContractDefined, double totalQuantity, double totalPrice,
	    List<BillingUnit> billingUnits) {
	super();
	this.currency = currency;
	this.ownContractDefined = ownContractDefined;
	this.totalQuantity = totalQuantity;
	this.totalPrice = totalPrice;
	this.billingUnits = billingUnits;
    }
}
