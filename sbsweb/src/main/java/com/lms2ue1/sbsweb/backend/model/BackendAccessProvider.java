package com.lms2ue1.sbsweb.backend.model;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;

import com.lms2ue1.sbsweb.backend.repository.*;

/** Provides communication between Frontend and Backend. */
public class BackendAccessProvider {

    //////////////////////// Repositories ////////////////////////

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

    //////////////////////// Singleton ////////////////////////

    private static BackendAccessProvider instance;

    /** Singleton creational pattern to get a BAP. */
    public static synchronized BackendAccessProvider getInstance() {
	if (instance == null) {
	    instance = new BackendAccessProvider();
	}
	return instance;
    }

    //////////////////////// Modifying the repositories ////////////////////////

    //////// Organisations

    /**
     * Adds a new organisation.
     * 
     * @param username        the username of the user requesting this operation.
     * @param newOrganisation the organisation to add.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void addOrganisation(String username, Organisation newOrganisation) {

    }

    /**
     * Removes an organisation.
     * 
     * @param username             the username of the user requesting this
     *                             operation.
     * @param organisationToRemove the organisation to remove.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void removeOrganisation(String username, String organisationToRemove) {

    }

    /**
     * Updates an organisation.
     * 
     * @param username            the username of the user requesting this
     *                            operation.
     * @param oldOrganisationId   the organisation's old id.
     * @param updatedOrganisation the updated organisation.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void updateOrganisation(String username, Long oldOrganisationId, Organisation updatedOrganisation) {

    }

    //////// Users

    /**
     * Adds a new user.
     * 
     * @param username the username of the user requesting this operation.
     * @param newUser  the user to add.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void addUser(String username, User newUser) {
	// TODO check if:
	// - username is allowed to add user
	// -> role has sufficient rights to add users
	// -> adds to same organisation (except for sysadmin)
	// - userToAdd doesn't already exist
	// -> id not yet in user repo
	// -> username not yet in user repo (case insensitive)
    }

    /**
     * Removes a user.
     * 
     * @param username     the username of the user requesting this operation.
     * @param userToRemove the user to remove.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void removeUser(String username, String userToRemove) {

    }

    /**
     * Updates a user.
     * 
     * @param username    the username of the user requesting this operation.
     * @param oldUserId   the user's old id.
     * @param updatedUser the updated user. Note that this user's username may not
     *                    be changed.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void updateUser(String username, Long oldUserId, User updatedUser) {

    }

    //////// Roles

    /**
     * Adds a new role.
     * 
     * @param username the username of the user requesting this operation.
     * @param newRole  the role to add.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void addRole(String username, Role newRole) {

    }

    /**
     * Removes a role.
     * 
     * @param username     the username of the user requesting this operation.
     * @param roleToRemove the role to remove.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void removeRole(String username, String roleToRemove) {

    }

    /**
     * Updates a role.
     * 
     * @param username    the username of the user requesting this operation.
     * @param oldRoleId   the role's old id.
     * @param updatedRole the updated role.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void updateRole(String username, Long oldRoleId, Role updatedRole) {

    }

    //////// BillingItem status

    /**
     * Updates a billing item's status.
     * 
     * @param username      the username of the user requesting this operation.
     * @param billingItemId the id of the billing item to update.
     * @param newStatus     the new status.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void updateStatus(String username, Long billingItemId, Status newStatus) {

    }

    //////////////////////// Getters per id ////////////////////////

    /**
     * Returns the address with the given id.
     * 
     * @param username  the username of the user requesting this operation.
     * @param addressId the address' id.
     * @return the address with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Address getAddressById(String username, Long addressId) {
	return null;
    }

    /**
     * Returns the project with the given id.
     * 
     * @param username  the username of the user requesting this operation.
     * @param projectId the project's id.
     * @return the project with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Project getProjectById(String username, Long projectId) {
	return null;
    }

    /**
     * Returns the contract with the given id.
     * 
     * @param username   the username of the user requesting this operation.
     * @param contractId the contract's id.
     * @return the contract with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Contract getContractById(String username, Long contractId) {
	return null;
    }

    /**
     * Returns the billing unit with the given id.
     * 
     * @param username      the username of the user requesting this operation.
     * @param billingUnitId the billing unit's id.
     * @return the billing unit with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public BillingUnit getBillingUnitById(String username, Long billingUnitId) {
	return null;
    }

    /**
     * Returns the billing item with the given id.
     * 
     * @param username      the username of the user requesting this operation.
     * @param billingItemId the billing item's id.
     * @return the billing item with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public BillingItem getBillingItemById(String username, Long billingItemId) {
	return null;
    }

    /**
     * Returns the organisation with the given id.
     * 
     * @param username       the username of the user requesting this operation.
     * @param organisationId the organisation's id.
     * @return the organisation with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Organisation getOrganisationById(String username, Long organisationId) {
	return null;
    }

    /**
     * Returns the user with the given id.
     * 
     * @param username the username of the user requesting this operation.
     * @param userId   the user's id.
     * @return the user with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public User getUserById(String username, Long userId) {
	return null;
    }

    /**
     * Returns the role with the given id.
     * 
     * @param username the username of the user requesting this operation.
     * @param roleId   the role's id.
     * @return the role with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Role getRoleById(String username, Long roleId) {
	return null;
    }

    //////////////////////// Getters for lists ////////////////////////

    /**
     * Returns a list of all addresses the user can access.
     * 
     * @param username the username of the user requesting this operation.
     * @return a list of all addresses the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Address> getAllAccessibleAddresses(String username) {
	return null;
    }

    /**
     * Returns a list of all projects the user can access.
     * 
     * @param username the username of the user requesting this operation.
     * @return a list of all projects the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Project> getAllAccessibleProjects(String username) {
	return null;
    }

    /**
     * Returns a list of all contracts the user can access.
     * 
     * @param username the username of the user requesting this operation.
     * @return a list of all contracts the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Contract> getAllAccessibleContracts(String username) {
	return null;
    }

    /**
     * Returns a list of all billing units the user can access.
     * 
     * @param username the username of the user requesting this operation.
     * @return a list of all billing units the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<BillingUnit> getAllAccessibleBillingUnits(String username) {
	return null;
    }

    /**
     * Returns a list of all billing items the user can access.
     * 
     * @param username the username of the user requesting this operation.
     * @return a list of all billing items the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<BillingItem> getAllAccessibleBillingItems(String username) {
	return null;
    }

    /**
     * Returns a list of all organisations the user can access.
     * 
     * @param username the username of the user requesting this operation.
     * @return a list of all organisations the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Organisation> getAllAccessibleOrganisations(String username) {
	return null;
    }

    /**
     * Returns a list of all users the user can access.
     * 
     * @param username the username of the user requesting this operation.
     * @return a list of all users the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<User> getAllAccessibleUsers(String username) {
	return null;
    }

    /**
     * Returns a list of all roles the user can access.
     * 
     * @param username the username of the user requesting this operation.
     * @return a list of all roles the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Role> getAllAccessibleRoles(String username) {
	return null;
    }
}
