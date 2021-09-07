package com.lms2ue1.sbsweb.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

 //   private Long organisationId;

    @NotEmpty
    private String surname;

    @NotEmpty
    private String lastname;

    @ManyToOne
    private Organisation organisation;

    @NotEmpty
    private String role;

    @NotEmpty
    private String username;

    public User() {
    }

    public User(String surname, String lastname, Organisation organisation, String role, String username) {
    //    this.organisationId = organisationId;
        this.surname = surname;
        this.lastname = lastname;
        this.organisation = organisation;
        this.role = role;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

  //  public Long getOrganisationId() {
//        return id;
//    }

    public String getSurname() {
        return surname;
    }

    public String getLastname() {
        return lastname;
    }

    public Organisation getOrganisation() {
        return organisation;
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

  //  public void setOrganisationId(Long organisationId) {
//        this.organisationId = organisationId;
//    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}