package com.lms2ue1.sbsweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long organisationId;

    @NotEmpty
    private String surname;

    @NotEmpty
    private String lastname;

    @NotEmpty
    private String organization;

    @NotEmpty
    private String role;

    @NotEmpty
    private String username;

    public User() {
    }

    public User(long organisationId, String surname, String lastname, String organization, String role, String username) {
        this.organisationId = organisationId;
        this.surname = surname;
        this.lastname = lastname;
        this.organization = organization;
        this.role = role;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public long getOrganisationId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getOrganization() {
        return organization;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOrganisationId(long organisationId) {
        this.organisationId = organisationId;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}