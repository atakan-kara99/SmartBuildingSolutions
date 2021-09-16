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
	    Organisation tmpOrga = orgaRepo.findByNameIgnoreCase(currentProject.getOwnerGroupIdentifier());
	    // Does this organisation already exist?
	    if (tmpOrga == null) {
		tmpOrga = new Organisation(currentProject.getOwnerGroupIdentifier());
		orgaRepo.save(tmpOrga);
	    }
	    currentProject.setOrganisation(tmpOrga);

	    // Get every status.
	    Status tmpStat = statRepo.findByNameIgnoreCase(currentProject.getAdessoStatus());
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
	    if (proRepo.findByAdessoID(currentProject.getAdessoID()).isEmpty()) {
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

	    // First: Let's set the project.
	    currentContract.setProject(proRepo.findByAdessoID(currentContract.getAdessoProjectId())
		    .orElseThrow(IllegalArgumentException::new));

	    // Second: Let's set the organisations.
	    Organisation contractor = orgaRepo.findByNameIgnoreCase(currentContract.getContractor());
	    if (contractor == null) {
		contractor = new Organisation(currentContract.getContractor());
		orgaRepo.save(contractor);
	    }

	    Organisation consignee = orgaRepo.findByNameIgnoreCase(currentContract.getConsignee());
	    if (consignee == null) {
		consignee = new Organisation(currentContract.getConsignee());
		orgaRepo.save(consignee);
	    }

	    currentContract.setOrganizations(List.of(contractor, consignee));

	    // Last, but not least, get the status:
	    Status tmpStat = statRepo.findByNameIgnoreCase(currentContract.getAdessoStatus());
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
	    // TODO: Merge -> What, if it already exists?

	    // --------------------- Now let's fetch everything else:
	    jsonDeserialiser.deserialiseBillingModelPerContract(currentContract.getAdessoID());
	}
    }

    // ------- Save the billing units with the repo --------//
    public void saveBillingUnits(BillingModel billingModel, long contractId) {
	if (billingModel == null) {
	    throw new IllegalArgumentException("Save billing model error: Object is <null>");
	}

	List<BillingUnit> billingUnits = billingModel.getBillingUnits();

	// ---- Iterate over each unit and save them via repository ----//
	for (BillingUnit currentBillingUnit : billingUnits) {
	    // first of all set the contract
	    Contract tmpContract = conRepo.findByAdessoID(contractId).orElseThrow(IllegalArgumentException::new);
	    currentBillingUnit.setContract(tmpContract);

	    // save the current billing unit
	    if (billingItemRepo.findByAdessoIDIgnoreCase(currentBillingUnit.getAdessoID()).isEmpty()) {
		billingUnitRepo.save(currentBillingUnit);
	    }
	    // TODO: Merge!

	    // saves the billing item of these billing unit
	    this.saveBillingItems(currentBillingUnit, currentBillingUnit.getBillingItems());
	}

    }

    // ------- Save the billing items with the repo --------//
    // ------- works with recursion --------//
    public void saveBillingItems(BillingUnit billingUnit, List<BillingItem> billingItems) {
	// ---- stop the recursion ----//
	if (billingItems == null || billingItems.isEmpty()) {
	    return;
	}
	// ---- Iterate over the billing items and store them via repository ----//
	for (BillingItem currentBillingItem : billingItems) {
	    currentBillingItem.setBillingUnit(billingUnit);

	    /* set the status of the billing item */
	    if (currentBillingItem.getAdessoStatus() != null) {
		currentBillingItem.setStatusObj(statRepo.findByNameIgnoreCase(currentBillingItem.getAdessoStatus()));
	    }

	    // ---- start the recursion for storing the sub billing items ----//
	    this.saveBillingItems(billingUnit, currentBillingItem.getBillingItems());

	    billingItemRepo.save(currentBillingItem);
	}
    }
}
