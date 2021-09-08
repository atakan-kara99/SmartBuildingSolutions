package com.lms2ue1.sbsweb.backend.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.lms2ue1.sbsweb.backend.repository.*;

/** Provides communication between Frontend and Backend. */
public class BackendAccessProvider {

    //////// Repositories ////////

    @Autowired
    AddressRepository addresses;
    @Autowired
    ProjectRepository projects;
    @Autowired
    ContractRepository contracts;
    @Autowired
    BillingUnitRepository billingUnits;
    @Autowired
    BillingItemRepository billingItems;
    @Autowired
    OrganisationRepository organisations;
    @Autowired
    UserRepository users;
    @Autowired
    RoleRepository roles;

    //////// Singleton ////////

    private static BackendAccessProvider instance;

    /** Singleton creational pattern to get a BAP. */
    public static synchronized BackendAccessProvider getInstance() {
	if (instance == null) {
	    instance = new BackendAccessProvider();
	}
	return instance;
    }

    //////// Methods ////////

    /**
     * Adds a new user.
     * 
     * @param username  the username of the user requesting this operation.
     * @param userToAdd the user to add.
     * @return the added user's id.
     */
    public Long addUser(String username, User userToAdd) {
	// TODO check if:
	// - username is allowed to add user
	// -> role has sufficient rights to add users
	// -> adds to same organisation (except for sysadmin)
	// - userToAdd doesn't already exist
	// -> id not yet in user repo
	// -> username not yet in user repo (case insensitive)
	return null;
    }

    /**
     * Removes a user.
     * 
     * @param username     the username of the user requesting this operation.
     * @param userToRemove the user to remove.
     */
    public void removeUser(String username, String userToRemove) {
    }

    /**
     * Updates a user.
     * 
     * @param username     the username of the user requesting this operation.
     * @param userToUpdate the updated user. Note that this user's username may not
     *                     be changed.
     * @return the added user's id.
     */
    public void updateUser(String username, User userToUpdate) {
    }
}
