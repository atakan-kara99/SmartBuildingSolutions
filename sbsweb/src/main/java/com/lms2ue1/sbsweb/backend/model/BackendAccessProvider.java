package com.lms2ue1.sbsweb.backend.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lms2ue1.sbsweb.backend.repository.*;
import com.lms2ue1.sbsweb.backend.security.AuthorisationCheck;

@Component
/** Provides communication between Frontend and Backend. */
public class BackendAccessProvider {

    // ---- Repositories ----//
    @Autowired
    private ProjectRepository projects;
    @Autowired
    private ContractRepository contracts;
    @Autowired
    private BillingUnitRepository billingUnits;
    @Autowired
    private BillingItemRepository billingItems;
    @Autowired
    private OrganisationRepository organisations;
    @Autowired
    private UserRepository users;
    @Autowired
    private RoleRepository roles;
    @Autowired
    private StatusRepository stati;

    // ---- Authorization check singleton ----//
    @Autowired
    private AuthorisationCheck auth;

    /** Disable public default constructor. */
    private BackendAccessProvider() {
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
    public void addOrganisation(String username, Organisation newOrganisation) throws AuthenticationException {
	if (auth.isSysAdmin(username)) {
	    organisations.save(newOrganisation);
	} else {
	    throw new AuthenticationException();
	}
    }

    /**
     * Removes an organisation.
     * 
     * @param username       the username of the user requesting this operation.
     * @param organisationId the id of the organisation to remove.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void removeOrganisation(String username, Long organisationId) throws AuthenticationException {
	if (auth.isSysAdmin(username)) {
	    organisations.deleteById(organisationId);
	} else {
	    throw new AuthenticationException();
	}
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
    public void updateOrganisation(String username, Long oldOrganisationId, Organisation updatedOrganisation)
	    throws AuthenticationException {
	if (auth.isSysAdmin(username)) {
	    Organisation oldOrganisation = organisations.findById(oldOrganisationId)
		    .orElseThrow(IllegalArgumentException::new);
	    oldOrganisation.setName(updatedOrganisation.getName());
	    organisations.save(oldOrganisation);
	} else {
	    throw new AuthenticationException();
	}
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
    public void addUser(String username, User newUser) throws AuthenticationException {
	if (newUser == null) {
	    throw new IllegalArgumentException();
	} else if (users.findByUsernameIgnoreCase(username) == null) {
	    throw new IllegalArgumentException("username is already taken!");
	}

	if (auth.canManageUser(username, newUser.getId())) {
	    users.save(newUser);
	} else {
	    throw new AuthenticationException();
	}
    }

    /**
     * Removes a user.
     * 
     * @param username the username of the user requesting this operation.
     * @param userId   the id of the user to remove.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void removeUser(String username, Long userId) throws AuthenticationException {
	if (userId == null) {
	    throw new IllegalArgumentException();
	}

	if (auth.canManageUser(username, userId)) {
	    users.deleteById(userId);
	} else {
	    throw new AuthenticationException();
	}
    }

    /**
     * Updates a user, including its role.
     * 
     * @param username    the username of the user requesting this operation.
     * @param oldUserId   the user's old id.
     * @param updatedUser the updated user. Note that this user's username may not
     *                    be changed.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void updateUser(String username, Long oldUserId, User updatedUser) throws AuthenticationException {
	if (oldUserId == null || updatedUser == null) {
	    throw new IllegalArgumentException();
	}

	if (auth.canManageUser(username, oldUserId)) {
	    User oldUser = users.findById(oldUserId).orElseThrow(IllegalArgumentException::new);
	    oldUser.setForename(updatedUser.getForename());
	    oldUser.setLastname(updatedUser.getLastname());
	    oldUser.setUsername(updatedUser.getUsername());
	    oldUser.setPassword(updatedUser.getPassword());
	    oldUser.setRole(updatedUser.getRole());
	    users.save(updatedUser);
	} else {
	    throw new AuthenticationException();
	}
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
    public void addRole(String username, Role newRole) throws AuthenticationException {
	if (newRole == null) {
	    throw new IllegalArgumentException();
	}

	boolean canSave = false;
	if (auth.isSysAdmin(username)) {
	    canSave = true;
	} else {
	    Long oID = auth.getOrgAdminID(username);
	    if (oID != null && newRole.getOrganisation().getId() == oID.longValue()) {
		canSave = true;
	    } else {
		throw new AuthenticationException();
	    }
	}

	if (canSave) {
	    roles.save(newRole);
	}
    }

    /**
     * Removes a role.
     * 
     * @param username the username of the user requesting this operation.
     * @param roleId   the id of the role to remove.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void removeRole(String username, Long roleId) throws AuthenticationException {
	if (roleId == null) {
	    throw new IllegalArgumentException();
	}

	boolean canDelete = false;
	if (auth.isSysAdmin(username)) {
	    canDelete = true;
	} else {
	    long oID1 = roles.findById(roleId).orElseThrow(IllegalArgumentException::new).getOrganisation().getId();
	    Long oID2 = auth.getOrgAdminID(username);
	    if (oID2 != null && oID1 == oID2.longValue()) {
		canDelete = true;
	    }
	    throw new AuthenticationException();
	}

	if (canDelete) {
	    roles.deleteById(roleId);
	}
    }

    /**
     * Updates a role, including its associated projects, contracts and billing
     * items.
     * 
     * @param username    the username of the user requesting this operation.
     * @param oldRoleId   the role's old id.
     * @param updatedRole the updated role.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void updateRole(String username, Long oldRoleId, Role updatedRole) throws AuthenticationException {
	if (oldRoleId == null || updatedRole == null) {
	    throw new IllegalArgumentException();
	}
	Role oldRole = roles.findById(oldRoleId).orElseThrow(IllegalArgumentException::new);

	boolean canUpdate = false;
	if (auth.isSysAdmin(username)) {
	    canUpdate = true;
	} else {
	    Long oID = auth.getOrgAdminID(username);
	    if (oID != null && oldRole.getOrganisation().getId() == oID) {
		canUpdate = true;
	    } else {
		throw new AuthenticationException();
	    }
	}

	if (canUpdate) {
	    oldRole.setName(updatedRole.getName());
	    oldRole.setManageUser(updatedRole.isManageUser());
	    oldRole.setName(updatedRole.getName());
	    oldRole.setProjects(updatedRole.getProjects());
	    oldRole.setContracts(updatedRole.getContracts());
	    oldRole.setBillingItems(updatedRole.getBillingItems());
	    roles.save(oldRole);
	}
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
    /*
     * public Address getAddressById(String username, Long addressId) throws
     * AuthenticationException { if (auth.checkAddress(username, addressId)) {
     * return
     * addresses.findById(addressId).orElseThrow(IllegalArgumentException::new); }
     * else { throw new AuthenticationException(); } }
     */

    /**
     * Returns the project with the given id.
     * 
     * @param username  the username of the user requesting this operation.
     * @param projectId the project's id.
     * @return the project with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Project getProjectById(String username, Long projectId) throws AuthenticationException {
	if (auth.checkProject(username, projectId)) {
	    return projects.findById(projectId).orElseThrow(IllegalArgumentException::new);
	} else {
	    throw new AuthenticationException();
	}
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
    public Contract getContractById(String username, Long contractId) throws AuthenticationException {
	if (auth.checkContract(username, contractId)) {
	    return contracts.findById(contractId).orElseThrow(IllegalArgumentException::new);
	} else {
	    throw new AuthenticationException();
	}
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
    public BillingUnit getBillingUnitById(String username, Long billingUnitId) throws AuthenticationException {
	if (auth.checkBillingUnit(username, billingUnitId)) {
	    return billingUnits.findById(billingUnitId).orElseThrow(IllegalArgumentException::new);
	} else {
	    throw new AuthenticationException();
	}
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
    public BillingItem getBillingItemById(String username, Long billingItemId) throws AuthenticationException {
	if (auth.checkBillingItem(username, billingItemId)) {
	    return billingItems.findById(billingItemId).orElseThrow(IllegalArgumentException::new);
	} else {
	    throw new AuthenticationException();
	}
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
    public Organisation getOrganisationById(String username, Long organisationId) throws AuthenticationException {
	if (auth.checkOrganisation(username, organisationId)) {
	    return organisations.findById(organisationId).orElseThrow(IllegalArgumentException::new);
	} else {
	    throw new AuthenticationException();
	}
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
    public User getUserById(String username, Long userId) throws AuthenticationException {
	User user = users.findById(userId).orElseThrow(IllegalArgumentException::new);
	if (auth.canManageUser(username, userId)) {
	    // Sys- or OrgAdmin
	    return user;
	} else if (userId != null && user.getId() == userId.longValue()) {
	    // User
	    return user;
	} else {
	    throw new AuthenticationException();
	}
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
    public Role getRoleById(String username, Long roleId) throws AuthenticationException {
	if (auth.isSysAdmin(username)) {
	    // Allmighty SysAdmin
	    return roles.findById(roleId).orElseThrow(IllegalArgumentException::new);
	} else if (users.findByUsername(username).getRole().getId() == roleId) {
	    // View own role
	    return roles.findById(roleId).orElseThrow(IllegalArgumentException::new);
	}
	// OrgAdmin
	Long oID = auth.getOrgAdminID(username);
	Role role = roles.findById(roleId).orElseThrow(IllegalArgumentException::new);
	if (oID != null && role.getId() == oID.longValue()) {
	    return role;
	} else {
	    throw new AuthenticationException();
	}
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
    public List<Address> getAllAddresses(String username) {
	try {
	    return getAllProjects(username).stream().map(p -> p.getAddress()).collect(Collectors.toList());
	    // if (auth.isSysAdmin(username)) {
	    // return StreamSupport.stream(addresses.findAll().spliterator(),
	    // false).collect(Collectors.toList());
	    // } else if (auth.getOrgAdminID(username) != null) {
	    // return
	    // users.findByUsername(username).getRole().getOrganisation().getProjects().stream()
	    // .map(p -> p.getAddress()).collect(Collectors.toList());
	    // }
	    // return users.findByUsername(username).getRole().getProjects().stream().map(p
	    // -> p.getAddress())
	    // .collect(Collectors.toList());
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
    public List<Project> getAllProjects(String username) {
	try {
	    if (auth.isSysAdmin(username)) {
		return StreamSupport.stream(projects.findAll().spliterator(), false).collect(Collectors.toList());
	    } else if (auth.getOrgAdminID(username) != null) {
		return users.findByUsername(username).getRole().getOrganisation().getProjects();
	    }
	    return users.findByUsername(username).getRole().getProjects();
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
    public List<Contract> getAllContracts(String username) {
	try {
	    return getAllProjects(username).stream().map(p -> p.getContracts()).flatMap(List::stream)
		    .collect(Collectors.toList());
	    // if (auth.isSysAdmin(username)) {
	    // return StreamSupport.stream(contracts.findAll().spliterator(),
	    // false).collect(Collectors.toList());
	    // } else if (auth.getOrgAdminID(username) != null) {
	    // return
	    // users.findByUsername(username).getRole().getOrganisation().getProjects().stream()
	    // .map(p ->
	    // p.getContracts()).flatMap(List::stream).collect(Collectors.toList());
	    // }
	    // return users.findByUsername(username).getRole().getContracts();
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
    public List<BillingUnit> getAllBillingUnits(String username) {
	try {
	    return getAllContracts(username).stream().map(c -> c.getBillingUnits()).flatMap(List::stream)
		    .collect(Collectors.toList());
	    // if (auth.isSysAdmin(username)) {
	    // return StreamSupport.stream(billingUnits.findAll().spliterator(),
	    // false).collect(Collectors.toList());
	    // } else if (auth.getOrgAdminID(username) != null) {
	    // return
	    // users.findByUsername(username).getRole().getOrganisation().getProjects().stream()
	    // .map(p -> p.getContracts()).flatMap(List::stream).map(c ->
	    // c.getBillingUnits())
	    // .flatMap(List::stream).collect(Collectors.toList());
	    // }
	    // return
	    // users.findByUsername(username).getRole().getBillingItems().stream().map(bi ->
	    // bi.getBillingUnit())
	    // .collect(Collectors.toList());
	} catch (NullPointerException e) {
	    throw new IllegalArgumentException();
	}
    }

    /**
     * Returns a list of all billing items the user can access, including nested
     * billing items.
     * 
     * @param username the username of the user requesting this operation.
     * @return a list of all billing items the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<BillingItem> getAllBillingItems(String username) {
	try {
	    return getAllBillingUnits(username).stream().map(bu -> bu.getBillingItems()).flatMap(List::stream)
		    .collect(Collectors.toList());
//	    if (auth.isSysAdmin(username)) {
//		return StreamSupport.stream(billingItems.findAll().spliterator(), false).collect(Collectors.toList());
//	    } else if (auth.getOrgAdminID(username) != null) {
//		return users.findByUsername(username).getRole().getOrganisation().getProjects().stream()
//			.map(p -> p.getContracts()).flatMap(List::stream).map(c -> c.getBillingUnits())
//			.flatMap(List::stream).map(bu -> bu.getBillingItems()).flatMap(List::stream)
//			.map(bi -> auth.flattenBillingItemsList(new ArrayList<>(), bi)).flatMap(List::stream)
//			.collect(Collectors.toList());
//	    }
//	    return users.findByUsername(username).getRole().getBillingItems().stream()
//		    .map(bi -> auth.flattenBillingItemsList(new ArrayList<>(), bi)).flatMap(List::stream)
//		    .collect(Collectors.toList());
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
    public List<Organisation> getAllOrganisations(String username) {
	try {
	    if (auth.isSysAdmin(username)) {
		// All organisations
		return StreamSupport.stream(organisations.findAll().spliterator(), false).collect(Collectors.toList());
	    }
	    // Own organisation
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
    public List<User> getAllUsers(String username) {
	try {
	    if (auth.isSysAdmin(username)) {
		// All users
		return StreamSupport.stream(users.findAll().spliterator(), false).collect(Collectors.toList());
	    } else if (auth.getOrgAdminID(username) != null) {
		// Users in organisation
		return users.findByUsername(username).getRole().getOrganisation().getRoles().stream()
			.map(r -> r.getUsers()).flatMap(List::stream).collect(Collectors.toList());
	    }
	    // Just the user
	    return List.of(users.findByUsername(username));
	} catch (NullPointerException e) {
	    throw new IllegalArgumentException();
	}
    }

    /**
     * Returns a list of all roles the user can access.
     * 
     * @param username the username of the user requesting this operation.
     * @return a list of all roles the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Role> getAllRoles(String username) {
	try {
	    if (auth.isSysAdmin(username)) {
		// All roles
		return StreamSupport.stream(roles.findAll().spliterator(), false).collect(Collectors.toList());
	    } else if (auth.getOrgAdminID(username) != null) {
		// Roles in organisation
		return users.findByUsername(username).getRole().getOrganisation().getRoles();
	    }
	    // Own role
	    return List.of(users.findByUsername(username).getRole());
	} catch (NullPointerException e) {
	    throw new IllegalArgumentException();
	}
    }

    //////////////////////// Frontend people love this stuff!
    //////////////////////// ////////////////////////
    //////////////////////// (Backend people too) ////////////////////////

    /**
     * Returns all users in the organisation with the given id.
     * 
     * @param username       the username of the user requesting this operation.
     * @param organisationId the organisation's id.
     * @return all users in the organisation with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<User> getAllUsersByOrganisationId(String username, Long organisationId) throws AuthenticationException {
	return getOrganisationById(username, organisationId).getRoles().stream().map(r -> r.getUsers())
		.flatMap(List::stream).collect(Collectors.toList());
    }

    //////// Add new billing item

    /**
     * Adds a new billing item.
     * 
     * @param username   the username of the user requesting this operation.
     * @param newBilling the billing item to add.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void addBillingItem(String username, BillingItem newBillingItem) throws AuthenticationException {
	if (newBillingItem == null) {
	    throw new IllegalArgumentException();
	}

	boolean canSave = false;
	if (auth.isSysAdmin(username)) {
	    canSave = true;
	} else {
	    Long oID = auth.getOrgAdminID(username);
	    if (oID != null && newBillingItem.getBillingUnit().getContract().getProject().getOrganisation()
		    .getId() == oID.longValue()) {
		canSave = true;
	    } else {
		throw new AuthenticationException();
	    }
	}

	if (canSave) {
	    billingItems.save(newBillingItem);
	}
    }

    //////// Stati

    /**
     * Adds a new status.
     * 
     * @param username  the username of the user requesting this operation.
     * @param newStatus the status to add.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void addStatus(String username, Status newStatus) throws AuthenticationException {
	if (newStatus == null) {
	    throw new IllegalArgumentException();
	}

	if (auth.isSysAdmin(username)) {
	    stati.save(newStatus);
	} else {
	    throw new AuthenticationException();
	}
    }

    /**
     * Removes a status.
     * 
     * @param username the username of the user requesting this operation.
     * @param statusId the id of the status to remove.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void removeStatus(String username, Long statusId) throws AuthenticationException {
	if (statusId == null) {
	    throw new IllegalArgumentException();
	}

	if (auth.isSysAdmin(username)) {
	    stati.deleteById(statusId);
	} else {
	    throw new AuthenticationException();
	}
    }

    /**
     * Updates a billing item's status.
     * 
     * @param username      the username of the user requesting this operation.
     * @param billingItemId the id of the billing item to update.
     * @param newStatus     the new status.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void updateBillingItemStatus(String username, Long billingItemId, Status newStatus)
	    throws AuthenticationException {
	if (users.findByUsernameIgnoreCase(username) == null) {
	    throw new AuthenticationException();
	} else if (billingItemId == null || newStatus == null) {
	    throw new IllegalArgumentException();
	}
	BillingItem billingItem = billingItems.findById(billingItemId).orElseThrow(IllegalArgumentException::new);
	billingItem.setStatusObj(newStatus);
	billingItems.save(billingItem);
    }

    /**
     * Returns the status with the given id.
     * 
     * @param statusId the status' id.
     * @return the status with the given id.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Status getStatusById(Long statusId) {
	return stati.findById(statusId).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Returns all stati in the StatusRepository (no duplicates). To get all stati
     * for all accessible projects (including duplicates), see
     * {@link #getAllStatiForAllProjects(String)}.
     * 
     * @return all stati.
     */
    public List<Status> getAllStati() {
	return StreamSupport.stream(stati.findAll().spliterator(), false).collect(Collectors.toList());
    }

    /**
     * Returns the status with the given id.
     * 
     * @param statusId the status' name.
     * @return the status with the given name.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Status getStatusByName(String statusName) {
	Status status = stati.findByName(statusName);
	if (status == null) {
	    throw new IllegalArgumentException();
	}
	return status;
    }

    /**
     * Maps the list of stati to a list of strings.
     * 
     * @param stati the list of stati.
     * @return the list of strings.
     */
    public List<String> getStatusStringList(List<Status> stati) {
	return stati.stream().map(s -> s.getName()).collect(Collectors.toList());
    }

    /**
     * Returns all stati as a list of strings. Order not specified. A status is
     * converted to string using only its name.
     * 
     * @return all stati as a list of strings.
     */
    public List<String> getAllStatiAsStrings() {
	return getStatusStringList(getAllStati());
    }

    /**
     * Returns all stati as a JSON list. Order not specified.
     * 
     * @return all stati as a JSON list.
     */
    public String getAllStatiAsJSON() {
	return "[" + String.join(", ", getAllStatiAsStrings()) + "]";
    }

    /**
     * Returns a list of all accessible stati in the contract with the given id.
     * 
     * @param username   the username of the user requesting this operation.
     * @param contractId the contract's id.
     * @return the list of all accessible stati in the contract with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Status> getAllStatiByContractId(String username, Long contractId) throws AuthenticationException {
	if (contractId == null) {
	    throw new IllegalArgumentException();
	}
	return getAllBillingItems(username).stream()
		.filter(bi -> bi.getBillingUnit().getContract().getInternId() == contractId.longValue())
		.map(bi -> bi.getStatusObj()).collect(Collectors.toList());
    }

    /**
     * Returns a list of all accessible stati in the project with the given id.
     * 
     * @param username  the username of the user requesting this operation.
     * @param projectId the project's id.
     * @return the list of all accessible stati in the project with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Status> getAllStatiByProjectId(String username, Long projectId) throws AuthenticationException {
	if (projectId == null) {
	    throw new IllegalArgumentException();
	}
	return getAllBillingItems(username).stream()
		.filter(bi -> bi.getBillingUnit().getContract().getProject().getInternID() == projectId.longValue())
		.map(bi -> bi.getStatusObj()).collect(Collectors.toList());
    }

    /**
     * Returns a list of all accessible stati (only stati in accessible projects).
     * To get <b> ALL </b> stati, see {@link #getAllStati()}.
     * 
     * @param username the username of the user requesting this operation.
     * @return the list of all accessible stati.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Status> getAllStatiForAllProjects(String username) throws AuthenticationException {
	return getAllBillingItems(username).stream().map(bi -> bi.getStatusObj()).collect(Collectors.toList());
    }
}
