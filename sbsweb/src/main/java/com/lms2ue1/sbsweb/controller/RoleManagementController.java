package com.lms2ue1.sbsweb.controller;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class RoleManagementController {

    // TODO Should be mostly the same as UserManagementController
    
    /** Shows an overview of an organisations' roles. */
    @GetMapping("/organisation/{oID}/role_management")
    public String showRoleList(@PathVariable Long oID, Model model) {
        return "role_management";
    }

    /** Shows the page to add a new role to an organisation. */
    @GetMapping("/organisation/{oID}/role_management/role_new")
    public String showNewRoleForm(@PathVariable Long oID, Model model) {
        return "role_new";
    }

    /** Will save the new role if no problems were encountered. Will also redirect to role management. */
//    @PostMapping("/organisation/{oID}/role_management/role_save")
//    public String addNewRole(@PathVariable Long oID, @Valid Role role, BindingResult bindingResult, Model model) {
//        //More checks needed
//        if(bindingResult.hasErrors()) {
//            model.addAttribute("role", role);
//            return "role_new";
//        }
//        return "redirect:/organisation/{oID}/role_management";
//    }

    /** Deletes the specified role and redirects to role management. */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/delete")
    public String deleteRoleById(@PathVariable Long oID, @PathVariable Long rID) {
        return "redirect:/organisation/{oID}/role_management";
    }

    /** Shows the role edit page */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_edit")
    public String showRoleById(@PathVariable Long oID, @PathVariable Long rID, Model model) {
        return "role_edit";
    }

    /** Updates the role with the new data that was specified. Will redirect to role management if everything went well. */
//    @PostMapping("/organisation/{oID}/role_management/role/{rID}/role_update")
//    public String editRoleById(@PathVariable Long oID, @PathVariable Long uID, @Valid Role role, BindingResult bindingResult, Model model) {
//        //More checks needed
//        if(bindingResult.hasErrors()) {
//            model.addAttribute("role", role);
//            return "role_edit";
//        }
//        return "redirect:/organisation/{oID}/role_management";
//    }
}
