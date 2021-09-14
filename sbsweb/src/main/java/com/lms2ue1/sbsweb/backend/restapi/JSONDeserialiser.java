package com.lms2ue1.sbsweb.backend.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms2ue1.sbsweb.backend.model.*;

public class JSONDeserialiser {
    
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    RESTDataRetriever restRetriever = new RESTDataRetriever();
    
    public List<Project> deserialiseProjects() {
	
	return mapper.readValue(restRetriever.fetchProjects(), Project[].class);
	
    }

}
