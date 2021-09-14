package com.lms2ue1.sbsweb.backend.restapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.repository.ProjectRepository;

@Component
public class JSONDeserialiser {

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    RESTDataRetriever restRetriever;

    @Bean
    CommandLineRunner deserialiseProjects(ProjectRepository projectRepository) {
	return args -> {
	    TypeReference<List<Project>> refProject = new TypeReference<List<Project>>() {
	    };

	    String json = restRetriever.fetchProjects();

	    InputStream inputStream = TypeReference.class.getResourceAsStream(json);
	    
	    projectRepository.saveAll(mapper.readValue(inputStream, refProject));
	};
    }

    // ---- Converts the rest api data to projects ----//
    /*
     * public List<Project> deserialiseProjects() throws JsonMappingException,
     * JsonProcessingException, IOException { TypeReference<List<Project>>
     * refProject = new TypeReference<List<Project>>() {};
     * 
     * String json = restRetriever.fetchProjects();
     * 
     * InputStream inputStream = TypeReference.class.getResourceAsStream(json);
     * 
     * return (mapper.readValue(inputStream, refProject)); }
     */

    // TODO: Implement me!
    // ---- Converts the rest api data to contracts ----//
    public void deserializeContracts() throws IOException {
	String json = restRetriever.fetchContracts(2);

	List<Contract> listContracts = Arrays.asList(mapper.readValue(json, Contract[].class));

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
