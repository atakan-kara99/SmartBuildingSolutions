package com.lms2ue1.sbsweb.backend.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.service.DBSynchronisationService;

@Component
public class JSONDeserialiser {

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    RESTDataRetriever restRetriever;
    @Autowired
    DBSynchronisationService dbUpdateService;

    @Bean
    CommandLineRunner deserialiseProjects(DBSynchronisationService dbUpdateService) {
	return args -> {
	    TypeReference<List<Project>> refProject = new TypeReference<List<Project>>() {
	    };

	    String json = restRetriever.fetchProjects();
	    
	    List<Project> listProjects = mapper.readValue(json, refProject);
	    
	    // In here: We will fetch everything else!
	   dbUpdateService.saveProjectList(listProjects);
	};
    }
    
    public void deserialiseContractsPerProject(long projectID) {
	//TODO: Implement me!
    }

}
