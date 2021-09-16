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
import com.lms2ue1.sbsweb.backend.model.User;
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
public class RoleBillingItemsController {
    @Autowired
    private BackendAccessProvider backendAccessProvider;

    @Autowired
    private AuthorisationCheck auth;

    /**
     * Displays the billing item access web page.
     * 
     * @param  principal               Security principal to access current user's name
     * @param  oID                     ID of the relevant organisation
     * @param  rID                     ID of the relevant role
     * @param  pID                     ID of the relevant project
     * @param  cID                     ID of the relevant contract
     * @param  model                   Spring model to provide instances to the web page
     * @throws AuthenticationException Thrown when current user ist not allowed to access specified organisation and role
     * @return                         URI of the HTML role edit billing item access page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/project/{pID}/contract/{cID}/role_edit_access_billing_items")
    public String showBillingItemsAccessPage(Principal principal, @PathVariable long oID, @PathVariable long rID, @PathVariable long pID,
    @PathVariable long cID, Model model) {
        Organisation organisation = null;
        Role role = null;
        Project project = null;
        Contract contract = null;
        List<BillingItem> availablebBillingItems = new ArrayList<BillingItem>();
        List<BillingItem> accessibleBillingItems = new ArrayList<BillingItem>();

        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
            project = backendAccessProvider.getProjectById(principal.getName(), pID);
            contract = backendAccessProvider.getContractById(principal.getName(), cID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }

        for(BillingItem availableBillingItem : backendAccessProvider.getAllBillingItems(principal.getName())) {
            if(availableBillingItem.getBillingUnit().getContract().getInternID() == cID) {
                availablebBillingItems.add(availableBillingItem);
            }
        }

        for(BillingItem roleBillingItem : role.getBillingItems()) {
            if(roleBillingItem.getBillingUnit().getContract().getInternID() == cID) {
                accessibleBillingItems.add(roleBillingItem);
            }
        }

        model.addAttribute("user", getUserByPrincipal(principal));
        model.addAttribute("adminPrivileges", auth.isSysAdmin(principal.getName()) || auth.getOrgAdminID(principal.getName()) != null);
        model.addAttribute("organisation", organisation);
        model.addAttribute("role", role);
        model.addAttribute("project", project);
        model.addAttribute("contract", contract);
        model.addAttribute("availableBillingItems", availablebBillingItems);
        model.addAttribute("accessibleBillingItems", accessibleBillingItems);
        return "role/role_edit_access_billing_items";
    }

    /**
     * GET mapping wotking a bit like a post mapping but without passed object. Adds a billing item to a given role by the IDs specified in the path.
     * 
     * @param  principal               Security principal to access current user's name
     * @param  oID                     ID of the relevant organisation
     * @param  rID                     ID of the relevant role
     * @param  pID                     ID of the relevant project
     * @param  cID                     ID of the relevant contract
     * @param  bID                     ID of the relevant billing item
     * @throws AuthenticationException Thrown when current user ist not allowed to access specified organisation and role
     * @return                         Redirect to the role edit contract access page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/project/{pID}/contract/{cID}/role_add_billing_item/billing_item/{bID}")
    public String addBillingItemToRole(Principal principal, @PathVariable long oID, @PathVariable long rID, @PathVariable long pID, @PathVariable long cID,
    @PathVariable long bID) {
        Role role = null;
        BillingItem billingItem = null;
        try {
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        try {
            billingItem = backendAccessProvider.getBillingItemById(principal.getName(), bID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        List<BillingItem> accessibleBillingItems = new ArrayList<BillingItem>(role.getBillingItems());
        accessibleBillingItems.add(billingItem);
        // TODO: Fix this mess somehow :D
        List<BillingItem> nestedBillingItemsToAdd = billingItem.getBillingItems().stream().map(bi -> auth.flattenBillingItemsList(new ArrayList<>(), bi)).flatMap(List::stream).collect(Collectors.toList());
        accessibleBillingItems.addAll(nestedBillingItemsToAdd);
        Role updatedRole =
        new Role(role.getName(), role.getProjects(), role.getContracts(), accessibleBillingItems, role.getOrganisation(), role.isManageUser());
        try {
            backendAccessProvider.updateRole(principal.getName(), role.getId(), updatedRole);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        return "redirect:/organisation/{oID}/role_management/role/{rID}/project/{pID}/contract/{cID}/role_edit_access_billing_items";
    }

    /**
     * GET mapping wotking a bit like a post mapping but without passed object. Removes a billing item of a given role by the IDs specified in the path.
     * 
     * @param  principal               Security principal to access current user's name
     * @param  oID                     ID of the relevant organisation
     * @param  rID                     ID of the relevant role
     * @param  pID                     ID of the relevant project
     * @param  cID                     ID of the relevant contract
     * @param  bID                     ID of the relevant billing item
     * @throws AuthenticationException Thrown when current user ist not allowed to access specified organisation and role
     * @return                         Redirect to the role edit contract access page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/project/{pID}/contract/{cID}/role_remove_billing_item/billing_item/{bID}")
    public String removeBillingItemFromRole(Principal principal, @PathVariable long oID, @PathVariable long rID, @PathVariable long pID, @PathVariable long cID,
    @PathVariable long bID) {
        Role role = null;
        BillingItem billingItem = null;
        try {
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        try {
            billingItem = backendAccessProvider.getBillingItemById(principal.getName(), bID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        List<BillingItem> accessibleBillingItems = new ArrayList<BillingItem>(role.getBillingItems());
        accessibleBillingItems.remove(billingItem);
        // TODO: Fix this mess somehow :D
        List<BillingItem> nestedBillingItemsToRemove = billingItem.getBillingItems().stream().map(bi -> auth.flattenBillingItemsList(new ArrayList<>(), bi)).flatMap(List::stream).collect(Collectors.toList());
        accessibleBillingItems.removeAll(nestedBillingItemsToRemove);
        Role updatedRole =
        new Role(role.getName(), role.getProjects(), role.getContracts(), accessibleBillingItems, role.getOrganisation(), role.isManageUser());
        try {
            backendAccessProvider.updateRole(principal.getName(), role.getId(), updatedRole);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        return "redirect:/organisation/{oID}/role_management/role/{rID}/project/{pID}/contract/{cID}/role_edit_access_billing_items";
    }

    /**
     * Method to get the User model object by using the principal.
     * 
     * @param  principal Security principal used to access the user model object
     * @return           The user model object when a corresponding user exists. Else null
     */
    private User getUserByPrincipal(Principal principal) {
        List<User> users = null;
        users = backendAccessProvider.getAllUsers(principal.getName());
        for (User user : users) {
            if (user.getUsername().equals(principal.getName())) {
                return user;
            }
        }
        return null;
    }
}