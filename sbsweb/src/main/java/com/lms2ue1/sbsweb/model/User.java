package com.lms2ue1.sbsweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private Long organisationId;

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
    
    // TODO: Is this decoded?
    @NotEmpty
    private String password;

    public User() {
    }

    public User(Long organisationId, String surname, String lastname, String organization, String role, String username, String password) {
        this.organisationId = organisationId;
        this.surname = surname;
        this.lastname = lastname;
        this.organization = organization;
        this.role = role;
        this.username = username;
        this.password = password;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public void setOrganisationId(Long organisationId) {
    	this.organisationId = organisationId;
    }

    public Long getId() {
        return id;
    }

    public Long getOrganisationId() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrganisationI(Long organisationId) {
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