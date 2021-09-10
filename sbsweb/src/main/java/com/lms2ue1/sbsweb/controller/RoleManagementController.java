package com.lms2ue1.sbsweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Role;
import com.lms2ue1.sbsweb.backend.model.User;
import com.lms2ue1.sbsweb.backend.repository.OrganisationRepository;
import com.lms2ue1.sbsweb.backend.repository.RoleRepository;
import com.lms2ue1.sbsweb.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RoleManagementController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    /** Shows an overview of <b> ALL </b> organisations' roles. */
    @GetMapping("/organisation/{oID}/role_management")
    public String showRoleList(@PathVariable long oID, Model model) {
        Organisation organisation = organisationRepository.findById(oID).get();
        List<Role> availableRoles = roleRepository.findByOrganisationOrderByNameAsc(organisation);
        model.addAttribute("roles", availableRoles);
        model.addAttribute("organisation", organisationRepository.findById(oID).get());
        return "role/role_management";
    }

    /**
     * Shows the page to add a new role to the specified organisation.
     * 
     * @param oID ID of the relevant organisation
     * @param model Spring model to provide instances to the web page
     * @return URI of the HTML role new page
     */
    @GetMapping("/organisation/{oID}/role_management/role_new")
    public String showNewRoleForm(@PathVariable long oID, Model model) {
        Organisation organisation = organisationRepository.findById(oID).get();
        Role newRole = new Role();
        newRole.setManageUser(true);
        model.addAttribute("organisation", organisation);
        model.addAttribute("role", newRole);
        return "role/role_new";
    }

    /**
     * POST mapping to add a new role to an organisation.
     * 
     * @param oID ID of the relevant organisation
     * @param role Instance of Role to add
     * @param bindingResult Binding results used for error checking
     * @param model Spring model to provide instances to the web page
     * @return When errors are found the URI to the HTML role new page is returned. Else a redirect to the organisation's role management page will be returned
     */
    @PostMapping("/organisation/{oID}/role_management/role_save")
    public String addNewRole(@PathVariable long oID, @Valid Role role, BindingResult bindingResult, Model model) {
        Organisation organisation = organisationRepository.findById(oID).get();
        if(bindingResult.hasErrors()) {
            model.addAttribute("role", role);
            model.addAttribute("organisation", organisation);
            return "role/role_new";
        }
        role.setOrganisation(organisation);
        roleRepository.save(role);
        return "redirect:/organisation/{oID}/role_management";
    }

    /** Deletes the specified role and redirects to role management. */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_delete")
    public String deleteRoleById(@PathVariable long oID, @PathVariable long rID) {
        roleRepository.deleteById(rID);
        return "redirect:/organisation/{oID}/role_management";
    }

    /**
     * Displays web page with a form to edit the selected role's name.
     * 
     * @param oID ID of the relevant organisation
     * @param rID ID of the selected role
     * @param model Spring model to provide instances to the web page
     * @return URI of the HTML role name edit page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_edit_name")
    public String showRoleEditFormById(@PathVariable long oID, @PathVariable long rID, Model model) {
        model.addAttribute("role", roleRepository.findById(rID).get());
        model.addAttribute("organisation", organisationRepository.findById(oID).get());
        return "role/role_edit_name";
    }

    /**
     * POST mapping to update the name of the relevant role.
     * 
     * @param oID ID of the relevant organisation
     * @param rID ID of the selected role
     * @param role Role instance containing updated name
     * @param bindingResult Binding results used for error checking
     * @param model Spring model to provide instances to the web page
     * @return Redirect to role management page
     */
    @PostMapping("/organisation/{oID}/role_management/role/{rID}/role_update_name")
    public String editRoleById(@PathVariable long oID, @PathVariable long rID, @Valid Role role, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("role", role);
            return "role/role_edit";
        }
        roleRepository.save(role);
        return "redirect:/organisation/{oID}/role_management";
    }

    /**
     * Displays the user editation page for the specified role.
     * 
     * @param oID ID of the relevant organisation
     * @param rID ID of the relevant role
     * @param model Spring model to provide instances to the web page
     * @return Redirect to role edit users page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_edit_users")
    public String showRoleUserEditPage(@PathVariable long oID, @PathVariable long rID, Model model) {
        List<User> roleUsers = roleRepository.findById(rID).get().getUsers();

        List<User> availableUsers = new ArrayList<User>();
        Organisation organisation = organisationRepository.findById(oID).get();
        List<Role> roles = roleRepository.findByOrganisationOrderByNameAsc(organisation);
        for (User user : userRepository.findAll()) {
            for(Role role : roles) {
                if(user.getRole().getId() == role.getId() && user.getRole().getId() != rID) {
                    availableUsers.add(user);
                }
            }
        }
        model.addAttribute("organisation", organisation);
        model.addAttribute("role", roleRepository.findById(rID).get());
        model.addAttribute("roleUsers", roleUsers);
        model.addAttribute("availableUsers", availableUsers);
        return "role/role_edit_users";
    }

    /**
     * GET mapping to add a new user to the relevant role. This actually sort of works like a post mapping.
     * 
     * @param oID ID of the relevant organisation
     * @param rID ID of the relevant role
     * @param uID ID of the specified user
     * @return Redirect to role edit users page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_add_user/user/{uID}")
    public String addUserToRole(@PathVariable long oID, @PathVariable long rID, @PathVariable long uID) {
        User user = userRepository.findById(uID).get();
        user.setRole(roleRepository.findById(rID).get());
        userRepository.save(user);
        return "redirect:/organisation/{oID}/role_management/role/{rID}/role_edit_users";
    }
}