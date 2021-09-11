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

    //////////////////////// Repositories ////////////////////////

    @Autowired
    private AddressRepository addresses;
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

    //////////////////////// Singleton using @Autowired ////////////////////////

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
     * @param  username                 the username of the user requesting this operation.
     * @param  newOrganisation          the organisation to add.
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
     * @param  username                 the username of the user requesting this operation.
     * @param  organisationId           the id of the organisation to remove.
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
     * @param  username                 the username of the user requesting this operation.
     * @param  oldOrganisationId        the organisation's old id.
     * @param  updatedOrganisation      the updated organisation.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void updateOrganisation(String username, Long oldOrganisationId, Organisation updatedOrganisation) throws AuthenticationException {
        if (auth.isSysAdmin(username)) {
            Organisation oldOrganisation = organisations.findById(oldOrganisationId).orElseThrow(IllegalArgumentException::new);
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
     * @param  username                 the username of the user requesting this operation.
     * @param  newUser                  the user to add.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void addUser(String username, User newUser) throws AuthenticationException {
        if (newUser == null) {
            throw new IllegalArgumentException();
        } else if (users.findByUsernameIgnoreCase(username) == null) {
            throw new IllegalArgumentException("username is already taken!");
        }

        if (auth.manageUser(username, newUser.getId())) {
            users.save(newUser);
        } else {
            throw new AuthenticationException();
        }
    }

    /**
     * Removes a user.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @param  userId                   the id of the user to remove.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void removeUser(String username, Long userId) throws AuthenticationException {
        if (userId == null) {
            throw new IllegalArgumentException();
        }

        if (auth.manageUser(username, userId)) {
            users.deleteById(userId);
        } else {
            throw new AuthenticationException();
        }
    }

    /**
     * Updates a user, including its role.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @param  oldUserId                the user's old id.
     * @param  updatedUser              the updated user. Note that this user's username may not be changed.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public void updateUser(String username, Long oldUserId, User updatedUser) throws AuthenticationException {
        if (oldUserId == null || updatedUser == null) {
            throw new IllegalArgumentException();
        }

        if (auth.manageUser(username, oldUserId)) {
            User oldUser = users.findById(oldUserId).orElseThrow(IllegalArgumentException::new);
            oldUser.setForename(updatedUser.getForename());
            oldUser.setLastname(updatedUser.getLastname());
            oldUser.setUsername(updatedUser.getUsername());
            oldUser.setPassword(updatedUser.getPassword());
            oldUser.setRole(updatedUser.getRole());
            users.save(oldUser);
        } else {
            throw new AuthenticationException();
        }
    }

    //////// Roles

    /**
     * Adds a new role.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @param  newRole                  the role to add.
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
     * @param  username                 the username of the user requesting this operation.
     * @param  roleId                   the id of the role to remove.
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
     * Updates a role, including its associated projects, contracts and billing items.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @param  oldRoleId                the role's old id.
     * @param  updatedRole              the updated role.
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

    //////// BillingItem status

    /**
     * Updates a billing item's status.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @param  billingItemId            the id of the billing item to update.
     * @param  newStatus                the new status.
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
     * @param  username                 the username of the user requesting this operation.
     * @param  addressId                the address' id.
     * @return                          the address with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Address getAddressById(String username, Long addressId) throws AuthenticationException {
        if (auth.checkAddress(username, addressId)) {
            return addresses.findById(addressId).orElseThrow(IllegalArgumentException::new);
        } else {
            throw new AuthenticationException();
        }
    }

    /**
     * Returns the project with the given id.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @param  projectId                the project's id.
     * @return                          the project with the given id.
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
     * @param  username                 the username of the user requesting this operation.
     * @param  contractId               the contract's id.
     * @return                          the contract with the given id.
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
     * @param  username                 the username of the user requesting this operation.
     * @param  billingUnitId            the billing unit's id.
     * @return                          the billing unit with the given id.
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
     * @param  username                 the username of the user requesting this operation.
     * @param  billingItemId            the billing item's id.
     * @return                          the billing item with the given id.
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
     * @param  username                 the username of the user requesting this operation.
     * @param  organisationId           the organisation's id.
     * @return                          the organisation with the given id.
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
     * @param  username                 the username of the user requesting this operation.
     * @param  userId                   the user's id.
     * @return                          the user with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public User getUserById(String username, Long userId) throws AuthenticationException {
        if (auth.manageUser(username, userId)) {
            return users.findById(userId).orElseThrow(IllegalArgumentException::new);
        } else {
            throw new AuthenticationException();
        }
    }

    /**
     * Returns the role with the given id.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @param  roleId                   the role's id.
     * @return                          the role with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Role getRoleById(String username, Long roleId) throws AuthenticationException {
        Long oID = auth.getOrgAdminID(username);
        Role role = roles.findById(roleId).orElseThrow(IllegalArgumentException::new);
        if ((oID != null && role.getId() == oID.longValue()) || auth.isSysAdmin(username)) {
            return role;
        } else {
            throw new AuthenticationException();
        }
    }

    /**
     * Returns the status with the given id.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @param  statusId                 the status' id.
     * @return                          the status with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public Status getStatusById(String username, Long statusId) {
        // TODO
        return null;
    }

    //////////////////////// Getters for lists ////////////////////////

    /**
     * Returns a list of all addresses the user can access.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @return                          a list of all addresses the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Address> getAllAddresses(String username) {
        try {
            return users.findByUsername(username).getRole().getProjects().stream().map(p -> p.getAddress()).collect(Collectors.toList());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns a list of all projects the user can access.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @return                          a list of all projects the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Project> getAllProjects(String username) {
        try {
            return users.findByUsername(username).getRole().getProjects();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns a list of all contracts the user can access.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @return                          a list of all contracts the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<Contract> getAllContracts(String username) {
        try {
            return users.findByUsername(username).getRole().getContracts();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns a list of all billing units the user can access.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @return                          a list of all billing units the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<BillingUnit> getAllBillingUnits(String username) {
        try {
            return users.findByUsername(username).getRole().getBillingItems().stream().map(bi -> bi.getBillingUnit()).collect(Collectors.toList());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns a list of all billing items the user can access, <b> not </b> including nested billing items.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @return                          a list of all billing items the user can access.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<BillingItem> getAllBillingItems(String username) {
        try {
            return users.findByUsername(username).getRole().getBillingItems();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns a list of all organisations the user can access.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @return                          a list of all organisations the user can access.
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
     * @param  username                 the username of the user requesting this operation.
     * @return                          a list of all users the user can access.
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
                return users.findByUsername(username).getRole().getOrganisation().getRoles().stream().map(r -> r.getUsers()).flatMap(List::stream)
                .collect(Collectors.toList());
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
     * @param  username                 the username of the user requesting this operation.
     * @return                          a list of all roles the user can access.
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

    //////////////////////// Convenience methods ////////////////////////

    /**
     * Returns all stati.
     * 
     * @return all stati.
     */
    public List<Status> getAllStati() {
        // TODO
        return null;
    }

    //////////////////////// Frontend people love this stuff!
    //////////////////////// ////////////////////////
    //////////////////////// (Backend people too) ////////////////////////

    /**
     * Returns all users in the organisation with the given id.
     * 
     * @param  username                 the username of the user requesting this operation.
     * @param  organisationId           the organisation's id.
     * @return                          all users in the organisation with the given id.
     * @throws AuthenticationException  if the user has insufficient rights.
     * @throws IllegalArgumentException if the operation failed.
     */
    public List<User> getAllUsersByOrganisationId(String username, Long organisationId) throws AuthenticationException {
        return getOrganisationById(username, organisationId).getRoles().stream().map(r -> r.getUsers()).flatMap(List::stream).collect(Collectors.toList());
    }

    //////////////////////// Stuff to JSON ////////////////////////

    /**
     * Serializes a status.
     * 
     * @param  status the status to serialize.
     * @return        the serialized status.
     */
    public String statusToJSON(Status status) {
        // TODO evtl schon irgendwas vordefiniert
        return null;
    }
}
