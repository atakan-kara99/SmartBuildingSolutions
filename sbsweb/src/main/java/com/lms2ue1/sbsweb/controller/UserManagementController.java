package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserManagementController {

    //// USERS ////

    /** Shows an overview of <b> ALL </b> organisations' users. */
    @GetMapping("/user_overview")
    public String showUserOverview(Model model) {
	// TODO sysadmin
	return "user_overview";
    }

    /** Shows an overview of the specified organisation's users. */
    @GetMapping("/organisation/{oID}/user_overview")
    public String showUserOverview(@PathVariable Long oID, Model model) {
	// TODO
	return "organisation_user_overview";
    }

    /** Shows the page to add a user to an organisation. */
    @GetMapping("/organisation/{oID}/add_user")
    public String showAddUser(@PathVariable Long oID, Model model) {
	// TODO
	return "organisation_add_user";
    }

    //// ROLES ////

    /** Shows an overview of <b> ALL </b> organisations' roles. */
    @GetMapping("/role_overview")
    public String showRoleOverview(Model model) {
	// TODO sysadmin
	return "role_overview";
    }

    /** Shows an overview of the specified organisation's roles. */
    @GetMapping("/organisation/{oID}/role_overview")
    public String showRoleOverview(@PathVariable Long oID, Model model) {
	// TODO
	return "organisation_role_overview";
    }

    /** Shows the page to add a role to an organisation. */
    @GetMapping("/organisation/{oID}/add_role")
    public String showAddRole(@PathVariable Long oID, Model model) {
	// TODO
	return "organisation_add_role";
    }
}