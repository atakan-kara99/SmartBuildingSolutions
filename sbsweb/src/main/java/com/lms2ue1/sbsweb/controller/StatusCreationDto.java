package com.lms2ue1.sbsweb.controller;

import java.util.List;

import com.lms2ue1.sbsweb.backend.model.Status;

public class StatusCreationDto {

    private String name;
    private String description;
    private List<Status> nextStati;

    public StatusCreationDto() {
    }

    public StatusCreationDto(String name, String description, List<Status> nextStati) {
	this.name = name;
	this.description = description;
	this.nextStati = nextStati;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addStatus(Status status) {
	this.nextStati.add(status);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Status> getNextStati() {
        return nextStati;
    }

    public void setNextStati(List<Status> stati) {
        this.nextStati = stati;
    }
}
