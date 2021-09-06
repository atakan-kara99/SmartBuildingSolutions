package com.lms2ue1.sbsweb.model;

import javax.validation.constraints.NotEmpty;

public class BillingItem {
    @NotEmpty
    private String name;

    private Long bID;

    private String shortDescription;

    private String status;

    public BillingItem(String name, Long bID) {
	this.name = name;
	this.bID = bID;
    }

    public BillingItem(String name, Long bID, String shortDescription, String status) {
	this.name = name;
	this.bID = bID;
	this.shortDescription = shortDescription;
	this.status = status;
    }

    public String getShortDescription() {
	return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
	this.shortDescription = shortDescription;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Long getbID() {
	return bID;
    }

    public void setbID(Long bID) {
	this.bID = bID;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }
}
