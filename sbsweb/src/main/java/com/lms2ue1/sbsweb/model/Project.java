package com.lms2ue1.sbsweb.model;

import javax.validation.constraints.NotEmpty;

public class Project {
    
    @NotEmpty
    private String name;

    private Long pID;

    public Project(String name, Long pID) {
	this.name = name;
	this.pID = pID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getpID() {
        return pID;
    }
}
