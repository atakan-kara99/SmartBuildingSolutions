package com.lms2ue1.sbsweb.backend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	// TODO sysadmin only
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
	// TODO sysadmin only
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
	// TODO sysadmin only
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
	// TODO
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
	// TODO
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
	// TODO
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
	// TODO
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
	// TODO
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
	// TODO
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
	// TODO check if username is allowed to access
	return addresses.findById(addressId).orElseThrow(IllegalArgumentException::new);
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
	// TODO check if username is allowed to access
	return projects.findById(projectId).orElseThrow(IllegalArgumentException::new);
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
	// TODO check if username is allowed to access
	return contracts.findById(contractId).orElseThrow(IllegalArgumentException::new);
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
	// TODO check if username is allowed to access
	return billingUnits.findById(billingUnitId).orElseThrow(IllegalArgumentException::new);
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
	// TODO check if username is allowed to access
	return billingItems.findById(billingItemId).orElseThrow(IllegalArgumentException::new);
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
	// TODO check if username is allowed to access
	return organisations.findById(organisationId).orElseThrow(IllegalArgumentException::new);
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
	// TODO check if username is allowed to access
	return users.findById(userId).orElseThrow(IllegalArgumentException::new);
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
	// TODO check if username is allowed to access
	return roles.findById(roleId).orElseThrow(IllegalArgumentException::new);
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
	try {
	    return users.findByUsername(username).getRole().getOrganisation().getContracts().stream()
		    .map(c -> c.getProject().getAddress()).collect(Collectors.toList());
	} catch (NullPointerException e) {
	    throw new IllegalArgumentException();
	}
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
	try {
	    return users.findByUsername(username).getRole().getOrganisation().getContracts().stream()
		    .map(c -> c.getProject()).collect(Collectors.toList());
	} catch (NullPointerException e) {
	    throw new IllegalArgumentException();
	}
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
	try {
	    return users.findByUsername(username).getRole().getOrganisation().getContracts();
	} catch (NullPointerException e) {
	    throw new IllegalArgumentException();
	}
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
	try {
	    return users.findByUsername(username).getRole().getOrganisation().getContracts().stream()
		    .map(c -> c.getBillingUnits()).flatMap(Collection::stream).collect(Collectors.toList());
	} catch (NullPointerException e) {
	    throw new IllegalArgumentException();
	}
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
	// TODO also return billing items in billing items? Not yet implemented
	try {
	    return users.findByUsername(username).getRole().getOrganisation().getContracts().stream()
		    .map(c -> c.getBillingUnits()).flatMap(List::stream).map(bu -> bu.getBillingItems())
		    .flatMap(List::stream).collect(Collectors.toList());
	} catch (NullPointerException e) {
	    throw new IllegalArgumentException();
	}
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
	// TODO can't handle the sysadmin's swag yet
	try {
	    return List.of(users.findByUsername(username).getRole().getOrganisation());
	} catch (NullPointerException e) {
	    throw new IllegalArgumentException();
	}
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
	// TODO
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
	// TODO
	return null;
    }
}
