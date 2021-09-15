package com.lms2ue1.sbsweb.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
@Entity
public class User {

    // ---- Attributes ----//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    private long id;
    @NotEmpty
    private String forename;
    @NotEmpty
    private String lastname;
    @NotEmpty
    @Column(unique = true)
    private String username;
    @NotEmpty
    private String password;

    // ---- Associations ----//
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // ----------------------------------//
    // ---------- Constructors ----------//
    // ----------------------------------//

    public User() {
    }

    /**
     * Constructor for OrgAdmin. He can create users.
     * 
     * @param firstname first name of the user.
     * @param lastname  last name of the user.
     * @param role      the role of the user.
     * @param username  user name.
     * @param password  password of the user (encrypted).
     */
    public User(String firstname, String lastname, Role role, String username, String password) {
	this.forename = firstname;
	this.lastname = lastname;
	this.role = role;
	this.username = username;
	this.password = password;
    }

    // ----------------------------//
    // ---------- Getter ----------//
    // ----------------------------//
    public Long getId() {
	return this.id;
    }

    public String getForename() {
	return this.forename;
    }

    public String getLastname() {
	return this.lastname;
    }

    public String getUsername() {
	return this.username;
    }

    public Role getRole() {
	return this.role;
    }

    public String getPassword() {
	return this.password;
    }

    // ----------------------------//
    // ---------- Setter ----------//
    // ----------------------------//
    public void setId(Long id) {
	this.id = id;
    }

    public void setForename(String fname) {
	this.forename = fname;
    }

    public void setLastname(String lname) {
	this.lastname = lname;
    }

    public void setUsername(String uname) {
	this.username = uname;
    }

    public void setRole(Role rs) {
	this.role = rs;
    }

    public void setPassword(String p) {
	this.password = p;
    }

    // ----------------------------//
    // ---------- Misc ------------//
    // ----------------------------//

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof User) {
	    User tmpUser = (User) obj;
	    return tmpUser.getId() == this.id;
	}
	return false;
    }

}