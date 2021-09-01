package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrganisationManagementController {

    /** Shows an overview of all organisations. */
    @GetMapping("/organisation_overview")
    public String showOrganisationOverview(Model model) {
	// TODO sysadmin
	return "organisation_overview";
    }
}