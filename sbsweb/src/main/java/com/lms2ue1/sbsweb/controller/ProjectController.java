package com.lms2ue1.sbsweb.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.backend.model.*;

@Controller
public class ProjectController {

    // Going to a billing item:
    // 1. Select a project from project_overview
    // 2. Select a contract from project_details
    // 3. Select a billing item from contract_details
    // 4. Select a nested billing item from billing_item_details (optional)
    // 5. Success: billing_item_details

    @Autowired
    private BackendAccessProvider BAP;

    /** Shows an overview of all projects. */
    @GetMapping("/project_overview")
    public String showProjectOverview(Principal principal, Model model) {
	try {
	    String username = principal.getName();
	    model.addAttribute("projects", BAP.getAllProjects(username));
	    return "project/project_overview";
	} catch (IllegalArgumentException e) {
	    return "error";
	}
    }

    /** Shows the specified project's details, e.g. its contracts. */
    @GetMapping("/project/{pID}/show")
    public String showProjectDetails(@PathVariable long pID, Principal principal, Model model) {
	try {
	    String username = principal.getName();
	    model.addAttribute("pID", pID);
	    model.addAttribute("project", BAP.getProjectById(username, pID));
	    List<Contract> contracts = BAP.getAllContracts(username);
	    model.addAttribute("contracts", contracts.stream().filter(contract -> contract.getProject().getId() == pID)
		    .collect(Collectors.toList()));
	    return "project/project_details";
	} catch (AuthenticationException | IllegalArgumentException e) {
	    return "error";
	}
    }
}