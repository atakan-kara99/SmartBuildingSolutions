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
	/**
	 * This class is a singleton.
	 */
	private static AuthorisationCheck instance;

	private AuthorisationCheck() {
	}

	/**
	 * Method to get the instance.
	 * 
	 * @return the instance
	 */
	public static AuthorisationCheck getAuthInstance() {
		if (instance == null) {
			synchronized (AuthorisationCheck.class) {
				if (instance == null) {
					instance = new AuthorisationCheck();
				}
			}
		}
		return instance;
	}

	// ---------- Repositories ------------- //
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	OrganisationRepository orgRepo;
	@Autowired
	ProjectRepository proRepo;
	@Autowired
	ContractRepository conRepo;
	@Autowired
	BillingItemRepository billItemRepo;
	@Autowired
	BillingUnitRepository billUnitRepo;
	@Autowired
	AddressRepository addRepo;

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
	 * Is the given user allowed to do anything with the other user?
	 * 
	 * @param username = the user, who wants to work with it.
	 * @param uID      = the other user to manage.
	 * @return true = yes, he*she is. false = no, he*she isn't.
	 */
	public boolean checkUser(String username, long uID) {
		// TODO: Implement me!
		return false;
	}

	/**
	 * Is the given user a SysAdmin?
	 * 
	 * @param username = the user in question.
	 * @return true = yes, he*she is. false = no, he*she isn't.
	 */
	public boolean isSysAdmin(String username) {
		return getRole(username).getName() == "SysAdmin";
	}

	/**
	 * Is the given user a OrgAdmin?
	 * 
	 * @param username = the user in question.
	 * @return true = yes, he*she is. false = no, he*she isn't.
	 */
	public boolean isOrgAdmin(String username) {
		// A SysAdmin is also an OrgAdmin.
		return getRole(username).getName() == "SysAdmin" || getRole(username).getName() == "OrgAdmin";
	}

	// TODO: The status => Different issue.

}
