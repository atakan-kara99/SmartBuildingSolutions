package com.lms2ue1.sbsweb.model;

import javax.validation.constraints.NotEmpty;

public class BillingItem {
    @NotEmpty
    private String name;

    private Long bID;

    public BillingItem(String name, Long bID) {
	this.name = name;
	this.bID = bID;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Long getBID() {
	return bID;
    }
}
