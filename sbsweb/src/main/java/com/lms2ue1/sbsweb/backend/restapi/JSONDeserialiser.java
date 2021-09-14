package com.lms2ue1.sbsweb.backend.restapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.lms2ue1.sbsweb.backend.model.*;

public class JSONDeserialiser {

	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	RESTDataRetriever restRetriever;

	// ---- Converts the rest api data to projects ----//
	public void deserialiseProjects() throws JsonMappingException, JsonProcessingException, IOException {
		String json = restRetriever.fetchProjects();
		
		List<Project> listProjects = Arrays.asList(mapper.readValue(json, Project.class));

		System.out.println(listProjects);


		// return listProjects;
	}
	// ---- Converts the rest api data to contracts ----//
	public void deserializeContracts() throws IOException {
		String json = restRetriever.fetchContracts(2);
		
	}
	// ---- Converts the rest api data to billung units ----//
	public void deserializeBillingUnits() throws IOException {
		String json = restRetriever.fetchBillingUnits(3);
	}
	
	// ---- Converts the rest api data to billing items ----//
	public void deserializeBillingItems() throws IOException {
		String json = restRetriever.fetchBillingItems(1);
	}


}
