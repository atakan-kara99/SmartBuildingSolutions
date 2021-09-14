package com.lms2ue1.sbsweb.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lms2ue1.sbsweb.backend.restapi.*;

@Service
public class DBSynchronisationService {
    
    @Autowired
    JSONDeserialiser jsonDeserialiser;
    
    public void init() throws JsonMappingException, JsonProcessingException, IOException {
	jsonDeserialiser.deserialiseProjects();
	
    }
}
