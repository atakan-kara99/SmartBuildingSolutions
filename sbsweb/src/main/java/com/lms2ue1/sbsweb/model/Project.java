package com.lms2ue1.sbsweb.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;

public class Project {
    
    @NotEmpty
    private String name;

    private Long pId;

    public Project(String name, Long pId) {
	this.name = name;
	this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getpId() {
        return pId;
    }
}
