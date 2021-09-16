package com.lms2ue1.sbsweb.backend.restapi;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    DBSynchronisationService dbSyncService;

    
    /**
     * Fetch all projects from the REST-API.
     * 
     * @throws IOException
     */
    //@Scheduled(fixedRate=5000)
    public void deserialiseProjects() throws IOException {

	TypeReference<List<Project>> refProject = new TypeReference<List<Project>>() {
	};

	String json = restRetriever.fetchProjects();

	if (!json.equals("{}")) {
	    List<Project> listProjects = mapper.readValue(json, refProject);

	    // In here: We will fetch everything else!
	    dbSyncService.saveProjectList(listProjects);
	}

    }

    /**
     * Fetch all the contracts of one given project from the REST-API.
     * 
     * @param projectID = The given project
     * @throws IOException
     */
    public void deserialiseContractsPerProject(long projectID) throws IOException {
	TypeReference<List<Contract>> refContract = new TypeReference<List<Contract>>() {
	};

	String json = restRetriever.fetchContracts(projectID);

	// A project can have 0 contracts!!
	if (!json.equals("{}")) {
	    List<Contract> listContracts = mapper.readValue(json, refContract);

	    dbSyncService.saveContractList(listContracts);
	}

    }

    /**
     * Fetch all the billing models of one given contract from the REST-API.
     * 
     * @param contractID = The given contract
     * @throws IOException
     */
    public void deserialiseBillingModelPerContract(long contractID) throws IOException {
	TypeReference<BillingModel> refBillingModel = new TypeReference<BillingModel>() {
	};

	String json = restRetriever.fetchBillingModel(contractID);

	if (!json.equals("{}")) {
	    BillingModel billingModel = mapper.readValue(json, refBillingModel);

	    dbSyncService.saveBillingUnits(billingModel, contractID);
	}

    }

}
