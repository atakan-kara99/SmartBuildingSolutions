package com.lms2ue1.sbsweb.backend.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.repository.*;

@Component
/**
 * This class provides predicates to say, whether a user is allowed to do
 * something.
 */
public class AuthorisationCheck {

    private AuthorisationCheck() {
    }

    // ---------- Repositories ------------- //
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private OrganisationRepository orgRepo;
    @Autowired
    private ProjectRepository proRepo;
    @Autowired
    private ContractRepository conRepo;
    @Autowired
    private BillingItemRepository billItemRepo;
    @Autowired
    private BillingUnitRepository billUnitRepo;
    @Autowired
    private AddressRepository addRepo;

    // ---------- Misc ------------- //

    /**
     * Get the role of the given user.
     * 
     * @param username = the given user.
     * @return the associated role.
     */
    public Role getRole(String username) {
	return userRepo.findByUsername(username).getRole();
    }

    // TODO: Effizienter direkt nach dem bID zu suchen.
    /**
     * Flatten the list of lists in one huge list.
     * 
     * @param hugeList       the future result.
     * @param currentElement the current element.
     * @return The flattened list.
     */
    public List<BillingItem> flattenBillingItemsList(List<BillingItem> hugeList, BillingItem currentElement) {
	// Using a dfs:
	hugeList.add(currentElement);
	if (currentElement.getBillingItems() != null) {
	    for (BillingItem nextElement : currentElement.getBillingItems()) {
		flattenBillingItemsList(hugeList, nextElement);
	    }
	}
	return hugeList;
    }

    // ---------- Organisation and stuff ------------- //

    /**
     * Is the given user allowed to do anything with the organisation?
     * 
     * @param username = the user, who wants to work with it.
     * @param oID      = the organisation in question.
     * @return true = yes, the user is. false = no, the user isn't.
     */
    public boolean checkOrganisation(String username, Long oID) {
	// Does the user belong to the given organisation?
	return getRole(username).getOrganisation()
		.equals(orgRepo.findById(oID).orElseThrow(IllegalArgumentException::new));
    }

    /**
     * Is the given user allowed to do anything with the project?
     * 
     * @param username = the user, who wants to work with it.
     * @param pID      = the project in question.
     * @return true = yes, the user is. false = no, the user isn't.
     */
    public boolean checkProject(String username, long pID) {
	return getRole(username).getProjects()
		.contains(proRepo.findById(pID).orElseThrow(IllegalArgumentException::new));
    }

    /**
     * Is the given user allowed to do anything with the contract?
     * 
     * @param username = the user, who wants to work with it.
     * @param cID      = the contract in question.
     * @return true = yes, the user is. false = no, the user isn't.
     */
    public boolean checkContract(String username, long cID) {
	return getRole(username).getContracts()
		.contains(conRepo.findById(cID).orElseThrow(IllegalArgumentException::new));
    }

    /**
     * Is the given user allowed to do anything with the billing unit?
     * 
     * @param username = the user, who wants to work with it.
     * @param buID     = the billing unit in question.
     * @return true = yes, the user is. false = no, the user isn't.
     */
    public boolean checkBillingUnit(String username, long buID) {
	// Every billing item in another billing item are part of the same billing unit.
	return getRole(username).getBillingItems().stream().map(bi -> bi.getBillingUnit()).collect(Collectors.toList())
		.contains(billUnitRepo.findById(buID).orElseThrow(IllegalArgumentException::new));
    }

    /**
     * Is the given user allowed to do anything with the billing item?
     * 
     * @param username = the user, who wants to work with it.
     * @param bID      = the billing item in question.
     * @return true = yes, the user is. false = no, the user isn't.
     */
    public boolean checkBillingItem(String username, long bID) {
	// We have billing items in billing items.
	// First: Get the the allowed "root" billing items of the role.
	// Second: Get the other leafs and flatten them to get one huge list.
	// Third: Check, whether the given billing item is part of that list.
	/*return getRole(username).getBillingItems().stream()
		.map(bi -> flattenBillingItemsList(new ArrayList<BillingItem>(), bi)).flatMap(List::stream)
		.collect(Collectors.toList())
		.contains(billItemRepo.findById(bID).orElseThrow(IllegalArgumentException::new));*/
	return getRole(username).getBillingItems().stream()
		.map(bi -> flattenBillingItemsList(new ArrayList<BillingItem>(), bi)).flatMap(List::stream)
		.anyMatch(bi -> bi.equals(billItemRepo.findById(bID).orElseThrow(IllegalArgumentException::new)));
    }

    /**
     * Is the given user allowed to do anything with the address?
     * 
     * @param username = the user, who wants to work with it.
     * @param aID      = the given address.
     * @return true = yes, the user is. false = no, the user isn't.
     */
    @Deprecated
    public boolean checkAddress(String username, long aID) {
	// Has the user the permission to see the project?
	Project project = addRepo.findById(aID).orElseThrow(IllegalArgumentException::new).getProject();
	return checkProject(username, project.getId());
    }

    // ---------- User management ------------- //

    /**
     * Is the given user allowed to manage the user?
     * 
     * @param username = the user, who wants to work with it.
     * @param uID      = the other user to manage.
     * @return true = yes, the user is. false = no, the user isn't.
     */
    public boolean canManageUser(String username, long uID) {
	// The SysAdmin is allowed to manage every user.
	// First: The given user has to have the permission to manage user per default.
	// Second: Both user have to be in the same organisation.
	return isSysAdmin(username) || (getRole(username).isManageUser() && getRole(username).getOrganisation()
		.equals(getRole(userRepo.findById(uID).orElseThrow(IllegalArgumentException::new).getUsername())
			.getOrganisation()));
    }

    /**
     * Is the given user a SysAdmin?
     * 
     * @param username = the user in question.
     * @return true = yes, the user is. false = no, the user isn't.
     */
    public boolean isSysAdmin(String username) {
	return getRole(username).getName().equals("SysAdmin");
    }

    /**
     * Get the organisation id, if the given user is an OrgAdmin. Otherwise, return
     * null.
     * 
     * @param username = the user in question.
     * @return oID of the OrgAdmin or null, if the user is not an OrgAdmin
     */
    public Long getOrgAdminID(String username) {
	return getRole(username).getName().equals("OrgAdmin") ? getRole(username).getOrganisation().getId() : null;
    }

    // TODO: The status => Different issue.

}
