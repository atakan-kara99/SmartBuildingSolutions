package com.lms2ue1.sbsweb.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import com.lms2ue1.sbsweb.model.Role;
import com.lms2ue1.sbsweb.repository.OrganisationRepository;
import com.lms2ue1.sbsweb.repository.RoleRepository;

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
    RoleRepository roleRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    /** Shows an overview of <b> ALL </b> organisations' roles. */
    @GetMapping("/organisation/{oID}/role_management")
    public String showUserList(@PathVariable long oID, Model model) {
        // Should be mostly part of backend
        List<Role> availableRoles = new ArrayList<Role>();
        Iterator<Role> iter = roleRepository.findAll().iterator();
        while(iter.hasNext()) {
            Role role = iter.next();
            if(role.getOrganisation().getId() == oID) {
                availableRoles.add(role);
            }
        }
        model.addAttribute("roles", availableRoles);
        model.addAttribute("organisation", organisationRepository.findById(oID).get());
        return "role_management";
    }

    /** Shows the page to add a new role to an organisation. */
    @GetMapping("/organisation/{oID}/user_management/role_new")
    public String showNewRoleForm(@PathVariable long oID, Model model) {
        model.addAttribute("role", new Role());
        return "role_new";
    }

    /** Will save the new role if no problems were encountered. Will also redirect to role management. */
    @PostMapping("/organisation/{oID}/user_management/role_save")
    public String addNewRole(@PathVariable long oID, @Valid Role role, BindingResult bindingResult, Model model) {
        //More checks needed
        if(bindingResult.hasErrors()) {
            model.addAttribute("role", role);
            return "role_new";
        }
        roleRepository.save(role);
        return "redirect:/organisation/{oID}/role_management";
    }

    /** Deletes the specified role and redirects to role management. */
    @GetMapping("/organisation/{oID}/user_management/role/{rID}/delete")
    public String deleteRoleById(@PathVariable long oID, @PathVariable long rID) {
        roleRepository.deleteById(rID);
        return "redirect:/organisation/{oID}/role_management";
    }

    /** Shows the role edit page */
    @GetMapping("/organisation/{oID}/user_management/role/{rID}/role_edit")
    public String showRoleEditFormById(@PathVariable long oID, @PathVariable long rID, Model model) {
        model.addAttribute("user", roleRepository.findById(rID).get());
        return "user_edit";
    }

    /** Updates the role with the new data that was specified. Will redirect to role management if everything went well. */
    @PostMapping("/organisation/{oID}/user_management/role/{rID}/role_update")
    public String editRoleById(@PathVariable long oID, @PathVariable long rID, @Valid Role role, BindingResult bindingResult, Model model) {
        //More checks needed
        if(bindingResult.hasErrors()) {
            model.addAttribute("role", role);
            return "role_edit";
        }
        role.setId(rID);
        roleRepository.save(role);
        return "redirect:/organisation/{oID}/role_management";
    }
}
