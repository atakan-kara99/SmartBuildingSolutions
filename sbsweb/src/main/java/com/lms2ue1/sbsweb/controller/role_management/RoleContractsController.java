package com.lms2ue1.sbsweb.controller.role_management;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import com.lms2ue1.sbsweb.backend.model.BackendAccessProvider;
import com.lms2ue1.sbsweb.backend.model.BillingItem;
import com.lms2ue1.sbsweb.backend.model.Contract;
import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Project;
import com.lms2ue1.sbsweb.backend.model.Role;
import com.lms2ue1.sbsweb.backend.repository.UserRepository;
import com.lms2ue1.sbsweb.backend.security.AuthorisationCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controller managing web page for contract related access management of a role
 * 
 * @author Luca Anthony Schwarz (sunfl0w)
 */
@Controller
public class RoleContractsController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BackendAccessProvider backendAccessProvider;

    @Autowired
    private AuthorisationCheck auth;

    /**
     * Displays the contract access web page.
     * 
     * @param  principal               Security principal to access current user's name
     * @param  oID                     ID of the relevant organisation
     * @param  rID                     ID of the relevant role
     * @param  pID                     ID of the relevant project
     * @param  model                   Spring model to provide instances to the web page
     * @throws AuthenticationException Thrown when current user ist not allowed to access specified organisation and role
     * @return                         URI of the HTML role edit contract access page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/project/{pID}/role_edit_access_contracts")
    public String showContractAccessPage(Principal principal, @PathVariable long oID, @PathVariable long rID, @PathVariable long pID, Model model) {
        Organisation organisation = null;
        Role role = null;
        Project project = null;
        List<Contract> availableContracts = backendAccessProvider.getAllContracts(principal.getName());
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        try {
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        try {
            project = backendAccessProvider.getProjectById(principal.getName(), pID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        // TODO Get user by name form BAP
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("adminPrivileges", auth.isSysAdmin(principal.getName()) || auth.getOrgAdminID(principal.getName()) != null);
        model.addAttribute("organisation", organisation);
        model.addAttribute("role", role);
        model.addAttribute("project", project);
        model.addAttribute("availableContracts", availableContracts);
        model.addAttribute("accessibleContracts", role.getContracts());
        return "role/role_edit_access_contracts";
    }

    /**
     * GET mapping wotking a bit like a post mapping but without passed object. Adds a contract to a given role by the IDs specified in the path.
     * 
     * @param  principal               Security principal to access current user's name
     * @param  oID                     ID of the relevant organisation
     * @param  rID                     ID of the relevant role
     * @param  pID                     ID of the relevant project
     * @param  cID                     ID of the relevant contract
     * @throws AuthenticationException Thrown when current user ist not allowed to access specified organisation and role
     * @return                         Redirect to the role edit contract access page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/project/{pID}/role_add_contract/contract/{cID}")
    public String addContractToRole(Principal principal, @PathVariable long oID, @PathVariable long rID, @PathVariable long pID, @PathVariable long cID) {
        Role role = null;
        Contract contract = null;
        try {
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        try {
            contract = backendAccessProvider.getContractById(principal.getName(), cID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        List<Contract> accessibleContracts = new ArrayList<Contract>(role.getContracts());
        accessibleContracts.add(contract);
        List<BillingItem> accessibleBillingItems = new ArrayList<BillingItem>(role.getBillingItems());
        // TODO: Fix this mess somehow :D
        List<BillingItem> billingItemsToAdd = contract.getBillingUnits().stream().map(bu -> bu.getBillingItems()).flatMap(List::stream)
        .map(bi -> auth.flattenBillingItemsList(new ArrayList<>(), bi)).flatMap(List::stream).collect(Collectors.toList());
        accessibleBillingItems.addAll(billingItemsToAdd);
        Role updatedRole =
        new Role(role.getName(), role.getProjects(), accessibleContracts, accessibleBillingItems, role.getOrganisation(), role.isManageUser());
        try {
            backendAccessProvider.updateRole(principal.getName(), role.getId(), updatedRole);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        return "redirect:/organisation/{oID}/role_management/role/{rID}/project/{pID}/role_edit_access_contracts";
    }

    /**
     * GET mapping wotking a bit like a post mapping but without passed object. Removes a contract of a given role by the IDs specified in the path.
     * 
     * @param  principal               Security principal to access current user's name
     * @param  oID                     ID of the relevant organisation
     * @param  rID                     ID of the relevant role
     * @param  pID                     ID of the relevant project
     * @param  cID                     ID of the relevant contract
     * @throws AuthenticationException Thrown when current user ist not allowed to access specified organisation and role
     * @return                         Redirect to the role edit contract access page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/project/{pID}/role_remove_contract/contract/{cID}")
    public String removeContractFromRole(Principal principal, @PathVariable long oID, @PathVariable long rID, @PathVariable long pID, @PathVariable long cID) {
        Role role = null;
        Contract contract = null;
        try {
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        try {
            contract = backendAccessProvider.getContractById(principal.getName(), cID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        List<Contract> accessibleContracts = new ArrayList<Contract>(role.getContracts());
        accessibleContracts.remove(contract);
        List<BillingItem> accessibleBillingItems = new ArrayList<BillingItem>(role.getBillingItems());
        // TODO: Fix this mess somehow :D
        List<BillingItem> billingItemsToRemove = contract.getBillingUnits().stream().map(bu -> bu.getBillingItems()).flatMap(List::stream)
        .map(bi -> auth.flattenBillingItemsList(new ArrayList<>(), bi)).flatMap(List::stream).collect(Collectors.toList());
        accessibleBillingItems.removeAll(billingItemsToRemove);
        Role updatedRole =
        new Role(role.getName(), role.getProjects(), accessibleContracts, role.getBillingItems(), role.getOrganisation(), role.isManageUser());
        try {
            backendAccessProvider.updateRole(principal.getName(), role.getId(), updatedRole);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        return "redirect:/organisation/{oID}/role_management/role/{rID}/project/{pID}/role_edit_access_contracts";
    }
}