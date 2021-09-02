package com.lms2ue1.sbsweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String name;

    @ManyToOne
    private Organisation organisation;

    public Role() {
    }

    public Role(String name, Organisation organisation) {
        this.name = name;
        this.organisation = organisation;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
}