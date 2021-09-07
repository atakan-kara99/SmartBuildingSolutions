package com.lms2ue1.sbsweb.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long oId;

    @NotEmpty
    private String orgName;

    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
    private List<User> users;

    public Organisation() {
    }

    public Organisation(Long oId, String orgName) {
        this.oId = oId;
        this.orgName = orgName;
        this.users = new ArrayList<User>();
    }

    public Long getOId() {
        return oId;
    }

    public String getOrgName() {
        return orgName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setOId(Long oID) {
        this.oId = oID;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setOrgAdministrator(User user) {
        user.setRole("OrgAdmin");
    }

}