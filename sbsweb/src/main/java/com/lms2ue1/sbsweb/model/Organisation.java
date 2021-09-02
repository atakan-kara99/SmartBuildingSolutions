package com.lms2ue1.sbsweb.model;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long organisationId;

    @NotEmpty
    private String orgName;

    public Organisation(){}

    public Organisation(Long organisationID, String orgName) {
        this.organisationId = organisationID;
        this.orgName = orgName;
    }

    public Long getOrganisationId() {
        return organisationId;
    }
    public String getOrgName(){
        return orgName;
    }
    public void setOrganisationId(Long organisationID){
        this.organisationId = organisationID;
    }
    public void setOrgName(String orgName){
        this.orgName = orgName;
    }
}