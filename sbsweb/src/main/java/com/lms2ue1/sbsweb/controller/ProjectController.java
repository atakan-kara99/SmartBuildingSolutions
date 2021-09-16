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
import com.lms2ue1.sbsweb.backend.repository.*;

@Controller
public class ProjectController {

    // Going to a billing item:
    // 1. Select a project from project_overview
    // 2. Select a contract from project_details
    // 3. Select a billing item from contract_details
    // 4. Select a nested billing item from billing_item_details (optional)
    // 5. Success: billing_item_details

    @Autowired
    ProjectRepository projects;
    @Autowired
    ContractRepository contracts;
    @Autowired
    RoleRepository roleRepo;

    @Autowired
    private BackendAccessProvider BAP;

    // List of temp stati
    List<String> listOfStatus = List.of("OK", "OK", "NO_STATUS", "OPEN", "OPEN", "DENY", "OPEN", "OK", "OK", "OK",
	    "NO_STATUS", "OK", "OK", "OK", "OPEN", "OK", "OK", "DENY");
    
    //List of list of status
    List<List<String>> listOfListOfStatus = List.of(listOfStatus,listOfStatus,listOfStatus,listOfStatus,listOfStatus,listOfStatus);

    /** Shows an overview of all projects. */
    @GetMapping("/project_overview")
    public String showProjectOverview(Principal principal, Model model) {
	try {
	    String username = principal.getName();
	    model.addAttribute("projects", BAP.getAllProjects(username));
	    model.addAttribute("listOfListOfStatus", listOfListOfStatus);
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
	    model.addAttribute("listOfListOfStatus", listOfListOfStatus);
	    return "project/project_details";
	} catch (AuthenticationException | IllegalArgumentException e) {
	    return "error";
	}
    }
}