package com.lms2ue1.sbsweb.controller.role_management;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import com.lms2ue1.sbsweb.backend.model.BackendAccessProvider;
import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Role;
import com.lms2ue1.sbsweb.backend.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller managing web page for displaying all roles of an organisation
 * 
 * @author Luca Anthony Schwarz (sunfl0w)
 */
@Controller
public class RoleManagementController {
    @Autowired
    private BackendAccessProvider backendAccessProvider;

    /**
     * Displays the organisation's role overview/management page
     * 
     * @param  principal Security principal to access current user's name
     * @param  oID       ID of the relevant organisation
     * @param  model     Spring model to provide instances to the web page
     * @return           URI of the HTML role management page or the unauthorised page
     */
    @GetMapping("/organisation/{oID}/role_management")
    public String showRoleList(Principal principal, @PathVariable long oID, Model model) {
        Organisation organisation = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        model.addAttribute("roles", organisation.getRoles());
        model.addAttribute("organisation", organisation);
        return "role/role_management";
    }

    /**
     * Shows the page to add a new role to the specified organisation.
     * 
     * @param  principal Security principal to access current user's name
     * @param  oID       ID of the relevant organisation
     * @param  model     Spring model to provide instances to the web page
     * @return           URI of the HTML role new page or the unauthorised page
     */
    @GetMapping("/organisation/{oID}/role_management/role_new")
    public String showNewRoleForm(Principal principal, @PathVariable long oID, Model model) {
        Organisation organisation = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        Role newRole = new Role();
        model.addAttribute("organisation", organisation);
        model.addAttribute("role", newRole);
        return "role/role_new";
    }

    /**
     * POST mapping to add a new role to an organisation.
     * 
     * @param  principal     Security principal to access current user's name
     * @param  oID           ID of the relevant organisation
     * @param  role          Instance of Role to add
     * @param  bindingResult Binding results used for error checking
     * @param  model         Spring model to provide instances to the web page
     * @return               When errors are found the URI to the HTML role new page is returned. Else a redirect to the organisation's role management page
     *                       will be returned when the user has sufficient privileges, otherwise a redirection to the unauthorised page will take place
     */
    @PostMapping("/organisation/{oID}/role_management/role_save")
    public String addNewRole(Principal principal, @PathVariable long oID, @Valid Role role, BindingResult bindingResult, Model model) {
        Organisation organisation = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("role", role);
            model.addAttribute("organisation", organisation);
            return "role/role_new";
        }
        Role roleToAdd = new Role(role.getName(), role.getProjects(), role.getContracts(), role.getBillingItems(), organisation, true);
        try {
            backendAccessProvider.addRole(principal.getName(), roleToAdd);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        return "redirect:/organisation/{oID}/role_management";
    }

    /**
     * Get mapping to delete a role by its id.
     * 
     * @param  principal Security principal to access current user's name
     * @param  oID       ID of the relevant organisation
     * @param  rID       ID of the relevant role
     * @return           Redirect the HTML role management page or the unauthorised page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_delete")
    public String deleteRoleById(Principal principal, @PathVariable long oID, @PathVariable long rID) {
        try {
            backendAccessProvider.removeRole(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        return "redirect:/organisation/{oID}/role_management";
    }

    /**
     * Displays web page with a form to edit the selected role's name.
     * 
     * @param  principal Security principal to access current user's name
     * @param  oID       ID of the relevant organisation
     * @param  rID       ID of the selected role
     * @param  model     Spring model to provide instances to the web page
     * @return           URI of the HTML role name edit page or the unauthorised page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_edit_name")
    public String showRoleEditFormById(Principal principal, @PathVariable long oID, @PathVariable long rID, Model model) {
        Organisation organisation = null;
        Role role = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        model.addAttribute("role", role);
        model.addAttribute("organisation", organisation);
        return "role/role_edit_name";
    }

    /**
     * POST mapping to update the name of the relevant role.
     * 
     * @param  principal     Security principal to access current user's name
     * @param  oID           ID of the relevant organisation
     * @param  rID           ID of the selected role
     * @param  role          Role instance containing updated name
     * @param  bindingResult Binding results used for error checking
     * @param  model         Spring model to provide instances to the web page
     * @return               Redirect to role management page or the unauthorised page
     */
    @PostMapping("/organisation/{oID}/role_management/role/{rID}/role_update_name")
    public String editRoleById(Principal principal, @PathVariable long oID, @PathVariable long rID, @Valid Role role, BindingResult bindingResult,
    Model model) {
        Organisation organisation = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("role", role);
            model.addAttribute("organisation", organisation);
            return "role/role_edit_name";
        }
        Role updatedRole = new Role(role.getName(), role.getProjects(), role.getContracts(), role.getBillingItems(), organisation, role.isManageUser());
        try {
            backendAccessProvider.updateRole(principal.getName(), rID, updatedRole);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        return "redirect:/organisation/{oID}/role_management";
    }

    /**
     * Displays the user editation page for the specified role.
     * 
     * @param  principal Security principal to access current user's name
     * @param  oID       ID of the relevant organisation
     * @param  rID       ID of the relevant role
     * @param  model     Spring model to provide instances to the web page
     * @return           URI of the HTML role edit users page or the unauthorised page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_edit_users")
    public String showRoleUserEditPage(Principal principal, @PathVariable long oID, @PathVariable long rID, Model model) {
        Organisation organisation = null;
        Role role = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }

        List<User> availableUsers = new ArrayList<User>();
        List<Role> roles = organisation.getRoles();
        for (Role otherRole : roles) {
            if (role.getId() != otherRole.getId()) {
                availableUsers.addAll(otherRole.getUsers());
            }
        }
        model.addAttribute("organisation", organisation);
        model.addAttribute("role", role);
        model.addAttribute("availableUsers", availableUsers);
        return "role/role_edit_users";
    }

    /**
     * GET mapping to add a new user to the relevant role. This actually sort of works like a post mapping.
     * 
     * @param  principal Security principal to access current user's name
     * @param  oID       ID of the relevant organisation
     * @param  rID       ID of the relevant role
     * @param  uID       ID of the specified user
     * @return           Redirect to role edit users page or the unauthorised page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_add_user/user/{uID}")
    public String addUserToRole(Principal principal, @PathVariable long oID, @PathVariable long rID, @PathVariable long uID) {
        User user = null;
        Role role = null;
        try {
            user = backendAccessProvider.getUserById(principal.getName(), uID);
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        User updatedUser = new User(user.getForename(), user.getLastname(), role, user.getUsername(), user.getPassword());
        try {
            backendAccessProvider.updateUser(principal.getName(), uID, updatedUser);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        return "redirect:/organisation/{oID}/role_management/role/{rID}/role_edit_users";
    }
}