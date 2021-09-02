package com.lms2ue1.sbsweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Organisation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}