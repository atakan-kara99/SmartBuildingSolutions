package com.lms2ue1.sbsweb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lms2ue1.sbsweb.backend.model.Project;
import com.lms2ue1.sbsweb.backend.repository.ProjectRepository;
import com.lms2ue1.sbsweb.backend.restapi.*;

@Service
public class DBSynchronisationService {

    @Autowired
    JSONDeserialiser jsonDeserialiser;

    @Autowired
    ProjectRepository proRepo;

    // TODO: Do we still need this service???
    public void init() throws JsonMappingException, JsonProcessingException, IOException {
	// TODO: How to save it properly???
	/*List<Project> listProjects = jsonDeserialiser.deserialiseProjects();

	proRepo.saveAll(listProjects);*/

    }
}
