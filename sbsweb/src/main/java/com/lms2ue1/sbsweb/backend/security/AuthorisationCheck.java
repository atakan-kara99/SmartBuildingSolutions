package com.lms2ue1.sbsweb.backend.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.repository.AddressRepository;
import com.lms2ue1.sbsweb.backend.repository.BillingItemRepository;
import com.lms2ue1.sbsweb.backend.repository.BillingUnitRepository;
import com.lms2ue1.sbsweb.backend.repository.ContractRepository;
import com.lms2ue1.sbsweb.backend.repository.OrganisationRepository;
import com.lms2ue1.sbsweb.backend.repository.ProjectRepository;
import com.lms2ue1.sbsweb.backend.repository.RoleRepository;
import com.lms2ue1.sbsweb.backend.repository.UserRepository;

/**
 * This class provides predicates to say, whether a user is allowed to do
 * something.
 */
public class AuthorisationCheck {

    private AuthorisationCheck() {
    }

    // ---------- Repositories ------------- //
    @Autowired
    private RoleRepository roleRepo;
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
    private Role getRole(String username) {
	return userRepo.findByUsername(username).getRole();
    }

    /**
     * Flatten the list of lists in one huge list.
     * 
     * @param hugeList = The list of lists to flatten.
     * @return The flattend list.
     */
    private <T> List<T> flattenBillingItems(List<T> hugeList) {
	// TODO: Implement me!
	return null;
    }

    // ---------- Organisation and stuff ------------- //

    /**
     * Is the given user allowed to do anything with the organisation?
     * 
     * @param username = the user, who wants to work with it.
     * @param oID      = the organisation in question.
     * @return true = yes, he*she is. false = no, he*she isn't.
     */
    public boolean checkOrganisation(String username, Long oID) {
	// Does the user belong to the given organisation?
	return getRole(username).getOrganisation().equals(orgRepo.findById(oID).get());
    }

    /**
     * Is the given user allowed to do anything with the project?
     * 
     * @param username = the user, who wants to work with it.
     * @param pID      = the project in question.
     * @return true = yes, he*she is. false = no, he*she isn't.
     */
    public boolean checkProject(String username, long pID) {
	return getRole(username).getProjects().contains(proRepo.findById(pID).get());
    }

    /**
     * Is the given user allowed to do anything with the contract?
     * 
     * @param username = the user, who wants to work with it.
     * @param cID      = the contract in question.
     * @return true = yes, he*she is. false = no, he*she isn't.
     */
    public boolean checkContract(String username, long cID) {
	return getRole(username).getContracts().contains(conRepo.findById(cID).get());
    }

    /**
     * Is the given user allowed to do anything with the billing unit?
     * 
     * @param username = the user, who wants to work with it.
     * @param buID     = the billing unit in question.
     * @return true = yes, he*she is. false = no, he*she isn't.
     */
    public boolean checkBillingUnit(String username, long buID) {
	// TODO: Verschachtelung
	// Has the user the permission to access an associated billing item?
	// First: The root billingItems.
	// Second: The other nodes.
	return getRole(username).getBillingItems().stream().map(b -> b.getBillingUnit()).collect(Collectors.toList())
		.contains(billUnitRepo.findById(buID).get())
		|| getRole(username).getBillingItems().stream().map(b -> b.getBillingItems()).flatMap(List::stream)
			.map(b -> b.getBillingUnit()).collect(Collectors.toList())
			.contains(billUnitRepo.findById(buID).get());
    }

    /**
     * Is the given user allowed to do anything with the billing item?
     * 
     * @param username = the user, who wants to work with it.
     * @param bID      = the billing item in question.
     * @return true = yes, he*she is. false = no, he*she isn't.
     */
    public boolean checkBillingItem(String username, long bID) {
	// TODO: Verschachtelung
	// We have billing items in billing items.
	// First: The root billingItems.
	// Second: The other nodes.
	return getRole(username).getBillingItems().contains(billItemRepo.findById(bID).get())
		|| getRole(username).getBillingItems().stream().map(b -> b.getBillingItems()).flatMap(List::stream)
			.collect(Collectors.toList()).contains(billItemRepo.findById(bID).get());
    }

    /**
     * Is the given user allowed to do anything with the address?
     * 
     * @param username = the user, who wants to work with it.
     * @param aID      = the given address.
     * @return true = yes, he*she is. false = no, he*she isn't.
     */
    public boolean checkAddress(String username, long aID) {
	// Has the user the permission to see the project?
	Project project = addRepo.findById(aID).get().getProject();
	return checkProject(username, project.getId());
    }

    // ---------- User management ------------- //

    /**
     * Is the given user allowed to manage the user?
     * 
     * @param username = the user, who wants to work with it.
     * @param uID      = the other user to manage.
     * @return true = yes, he*she is. false = no, he*she isn't.
     */
    public boolean manageUser(String username, long uID) {
	// First: The given user has to have the permission to manage user per default.
	// Second: Both user have to be in the same organisation.
	return getRole(username).isManageUser() && getRole(username).getOrganisation()
		.equals(getRole(userRepo.findById(uID).get().getUsername()).getOrganisation());
    }

    /**
     * Is the given user a SysAdmin?
     * 
     * @param username = the user in question.
     * @return true = yes, he*she is. false = no, he*she isn't.
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
