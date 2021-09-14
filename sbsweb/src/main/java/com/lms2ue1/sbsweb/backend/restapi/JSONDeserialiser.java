package com.lms2ue1.sbsweb.backend.restapi;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms2ue1.sbsweb.backend.model.*;

public class JSONDeserialiser {
    
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    RESTDataRetriever restRetriever = new RESTDataRetriever();
    
    public List<Project> deserialiseProjects() throws JsonMappingException, JsonProcessingException, IOException {
	// TODO: Alle Assoziationen und adesso-ID!
	// TODO: JSON-Properties!
	return Arrays.asList(mapper.readValue(restRetriever.fetchProjects(), Project[].class));
	
    }

}
