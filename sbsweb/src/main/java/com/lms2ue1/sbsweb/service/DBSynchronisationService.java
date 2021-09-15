package com.lms2ue1.sbsweb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms2ue1.sbsweb.backend.model.BillingItem;
import com.lms2ue1.sbsweb.backend.model.BillingModel;
import com.lms2ue1.sbsweb.backend.model.BillingUnit;
import com.lms2ue1.sbsweb.backend.model.Contract;
import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Project;
import com.lms2ue1.sbsweb.backend.model.Status;
import com.lms2ue1.sbsweb.backend.repository.BillingItemRepository;
import com.lms2ue1.sbsweb.backend.repository.BillingUnitRepository;
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
    @Autowired
    private BillingUnitRepository billingUnitRepo;
    @Autowired
    private BillingItemRepository billingItemRepo;

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
	    if (proRepo.findByName(currentProject.getName()).isEmpty()) {
		proRepo.save(currentProject);
	    }
	    // TODO: What, if it already exists?

	    // --------------------- Now let's fetch everything else:
	    jsonDeserialiser.deserialiseContractsPerProject(currentProject.getAdessoID());

	}

    }

    /**
     * Save the contracts for each project.
     * 
     * @param contractList = All the contracts of one specific project.
     * @throws IOException
     */
    public void saveContractList(List<Contract> contractList) throws IOException {
	if (contractList == null) {
	    throw new IllegalArgumentException("Save contractList error: List is <null>");
	}

	// Get all the dependencies:
	for (Contract currentContract : contractList) {

	    // TODO: ---- testing ----//
	    /*
	     * System.out.println("Current id of the contract: " +
	     * currentContract.getAdessoID());
	     * System.out.println("Current name of the contract" +
	     * currentContract.getName());
	     */

	    // First: Let's set the project.
	    currentContract.setProject(proRepo.findByAdessoID(currentContract.getAdessoProjectId())
		    .orElseThrow(IllegalArgumentException::new));

	    // Second: Let's set the organisations.
	    Organisation contractor = orgaRepo.findByName(currentContract.getContractor());
	    if (contractor == null) {
		contractor = new Organisation(currentContract.getContractor());
		orgaRepo.save(contractor);
	    }

	    Organisation consignee = orgaRepo.findByName(currentContract.getConsignee());
	    if (consignee == null) {
		consignee = new Organisation(currentContract.getConsignee());
		orgaRepo.save(consignee);
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
	    if (conRepo.findByAdessoID(currentContract.getAdessoID()).isEmpty()) {
		conRepo.save(currentContract);
	    }
	    // TODO: What, if it already exists?

	    // --------------------- Now let's fetch everything else:
	    jsonDeserialiser.deserialiseBillingModelPerContract(currentContract.getAdessoID());
	}
    }

    // ------- Save the billing units with the repo --------//
    public void saveBillingUnits(BillingModel billingModel, long contractId) {
	List<BillingUnit> billingUnits = billingModel.getBillingUnits();

	// TODO : (optional) throw illegal argument exception if list is empty => If
	// empty, the foreach wouldn't start (I hope)

	for (BillingUnit currentBillingUnit : billingUnits) {
	    // first of all set the contract
	    
	    Contract tmpContract = conRepo.findByAdessoID(contractId).orElseThrow(IllegalArgumentException::new);
	    currentBillingUnit.setContract(tmpContract);

	    // TODO: ---- testing ----//
	    //System.out.println("Current contract id: " + tmpContract.getName());

	    if (billingItemRepo.findByAdessoID(currentBillingUnit.getAdessoID()).isEmpty()) {
		billingUnitRepo.save(currentBillingUnit);
	    }
	    //TODO: Merge!

	    // TODO after saving the billing units save the billing items
	    // saves the current billing item too
	    this.saveBillingItems(currentBillingUnit, currentBillingUnit.getBillingItems());
	}

    }

    // ------- Save the billing items with the repo --------//
    public void saveBillingItems(BillingUnit billingUnit, List<BillingItem> billingItems) {
	// Breakpoint!
	if (billingItems == null || billingItems.isEmpty()) {
	    return;
	}
	
	// TODO : (optional) throw illegal argument exception if list is empty
	// TODO: DEBUGG
	for (BillingItem b : billingItems) {
	    System.out.println(b.getName());
	}

	for (BillingItem currentBillingItem : billingItems) {
	    // The billingitem stores the billingunit!
	    // currentBillingItem.setBillingUnit(billingUnitRepo.findByAdessoID(billingUnit.getAdessoID()));
	    currentBillingItem.setBillingUnit(billingUnit);

	    if (currentBillingItem.getAdessoStatus() != null) {
		currentBillingItem.setStatusObj(statRepo.findByName(currentBillingItem.getAdessoStatus()));;
	    }
	    
	    this.saveBillingItems(billingUnit, currentBillingItem.getBillingItems());

	    billingItemRepo.save(currentBillingItem);
	}
    }
}
