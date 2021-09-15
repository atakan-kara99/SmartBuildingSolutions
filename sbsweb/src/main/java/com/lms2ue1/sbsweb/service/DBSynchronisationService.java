package com.lms2ue1.sbsweb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms2ue1.sbsweb.backend.model.Contract;
import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Project;
import com.lms2ue1.sbsweb.backend.model.Status;
import com.lms2ue1.sbsweb.backend.repository.ContractRepository;
import com.lms2ue1.sbsweb.backend.repository.OrganisationRepository;
import com.lms2ue1.sbsweb.backend.repository.ProjectRepository;
import com.lms2ue1.sbsweb.backend.repository.StatusRepository;
import com.lms2ue1.sbsweb.backend.restapi.JSONDeserialiser;

@Service
public class DBSynchronisationService {
    
    @Autowired
    JSONDeserialiser jsonDeserialiser;

    // ---------- Repositories ---------//
    @Autowired
    private ContractRepository conRepo;
    @Autowired
    private OrganisationRepository orgaRepo;
    @Autowired
    private ProjectRepository proRepo;
    @Autowired
    private StatusRepository statRepo;

    // ---------- Methods ------------//

    /**
     * Save the new projects in the database.
     * 
     * @param projectlist = The given projects from the REST-API.
     * @throws IOException 
     */
    public void saveProjectList(List<Project> projectlist) throws IOException {
	if (projectlist == null) {
	    throw new IllegalArgumentException("Save projectlist error: List is <null>");
	}
	
	// Get all the dependencies:
	for (Project currentProject : projectlist) {
	    // Get every organisation.
	    Organisation tmpOrga = orgaRepo.findByName(currentProject.getOwnerGroupIdentifier());
	    // Does this organisation already exist?
	    if (tmpOrga == null) {
		tmpOrga = new Organisation(currentProject.getOwnerGroupIdentifier());
		orgaRepo.save(tmpOrga);
	    }
	    currentProject.setOrganisation(tmpOrga);

	    // Get every status.
	    Status tmpStat = statRepo.findByName(currentProject.getAdessoStatus());
	    // Does this status already exist?
	    if (tmpStat == null) {
		tmpStat = new Status(currentProject.getAdessoStatus(), null);
		statRepo.save(tmpStat);
	    }
	    currentProject.setStatusObj(tmpStat);

	    // TODO: Überarbeiten!
	    // Some descriptions are too long:
	    String tmpStr;
	    if (currentProject.getDescription().length() > 255) {
		tmpStr = currentProject.getDescription().substring(0, 254);
		currentProject.setDescription(tmpStr);
	    }
	    
	    // --------------------- Actually store everything!
	    // Only save it, if it doesn't already exist:
	    if (proRepo.findByName(currentProject.getName()) == null) {
		proRepo.save(currentProject);
	    }
	    // TODO: What, if it already exists?
	    
	    
	    // --------------------- Now let's fetch everything else:
	    jsonDeserialiser.deserialiseContractsPerProject(currentProject.getAdessoID());
	    
	    // TODO: Implement BillingItems and BillingUnits!
	}

    }

    /**
     * Save the contracts for each project.
     * 
     * @param contractList = All the contracts of one specific project.
     */
    public void saveContractList(List<Contract> contractList) {
	if (contractList == null) {
	    throw new IllegalArgumentException("Save contractList error: List is <null>");
	}

	// Get all the dependencies:
	for (Contract currentContract : contractList) {

	    // First: Let's set the project.
	    currentContract.setProject(
		    proRepo.findById(currentContract.getId()).orElseThrow(IllegalArgumentException::new));

	    // Second: Let's set the organisations.
	    Organisation contractor = orgaRepo.findByName(currentContract.getContractor());
	    if (contractor == null) {
		contractor = new Organisation(currentContract.getContractor());
		orgaRepo.save(contractor);
	    }

	    Organisation consignee = orgaRepo.findByName(currentContract.getConsignee());
	    if (consignee == null) {
		contractor = new Organisation(currentContract.getConsignee());
		orgaRepo.save(contractor);
	    }

	    currentContract.setOrganizations(List.of(contractor, consignee));
	    
	    // Last, but not least, get the status:
	    Status tmpStat = statRepo.findByName(currentContract.getAdessoStatus());
	    if (tmpStat == null) {
		tmpStat = new Status(currentContract.getAdessoStatus(), null);
		statRepo.save(tmpStat);
	    }
	    currentContract.setStatusObj(tmpStat);

	    // Some descriptions are too long:
	    String tmpStr;
	    if (currentContract.getDescription().length() > 255) {
		tmpStr = currentContract.getDescription().substring(0, 254);
		currentContract.setDescription(tmpStr);
	    }
	    
	    // --------------------- Actually store everything!
	    // Only save it, if it doesn't already exist:
	    if (conRepo.findByName(currentContract.getName()) == null) {
		conRepo.save(currentContract);
	    }
	    // TODO: What, if it already exists?
	}
    }
}
