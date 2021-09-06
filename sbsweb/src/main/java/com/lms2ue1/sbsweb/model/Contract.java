package com.lms2ue1.sbsweb.model;

import javax.validation.constraints.NotEmpty;

public class Contract {
    @NotEmpty
    private String name;

    private Long cID;

    public Contract(String name, Long cID) {
	this.name = name;
	this.cID = cID;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Long getCID() {
	return cID;
    }
}
