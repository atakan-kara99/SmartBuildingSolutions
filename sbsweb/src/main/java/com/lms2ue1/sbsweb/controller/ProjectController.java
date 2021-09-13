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

    // List of temp stati for overview
    List<String> listOfStatus1 = List.of("OK", "OK", "NO_STATUS", "OPEN", "OPEN", "DENY", "OPEN", "OK", "OK", "OK",
	    "NO_STATUS", "OK", "OK", "OK", "OPEN", "OK", "OK", "DENY", "OK", "OK", "NO_STATUS", "OPEN", "OPEN", "DENY",
	    "OPEN", "OK", "OK", "OK", "NO_STATUS", "OK", "OK", "OK", "OPEN", "OK", "OK", "DENY", "OK", "OK",
	    "NO_STATUS", "OPEN", "OPEN", "DENY", "OPEN", "OK", "OK", "OK", "NO_STATUS", "OK", "OK", "OK", "OPEN", "OK",
	    "OK", "DENY", "OK", "OK", "NO_STATUS", "OPEN", "OPEN", "DENY", "OPEN", "OK", "OK", "OK", "NO_STATUS", "OK",
	    "OK", "OK", "OPEN", "OK", "OK", "DENY", "OK", "OK", "NO_STATUS", "OPEN", "OPEN", "DENY", "OPEN", "OK", "OK",
	    "OK", "NO_STATUS", "OK", "OK", "OK", "OPEN", "OK", "OK", "DENY", "OK", "OK", "NO_STATUS", "OPEN", "OPEN",
	    "DENY", "OPEN", "OK", "OK", "OK", "NO_STATUS", "OK", "OK", "OK", "OPEN", "OK", "OK", "DENY");

    /** Shows an overview of all projects. */
    @GetMapping("/project_overview")
    public String showProjectOverview(Principal principal, Model model) {
	try {
	    String username = principal.getName();
	    model.addAttribute("projects", BAP.getAllProjects(username));
	    model.addAttribute("listOfStatus", listOfStatus1);
	    return "project/project_overview";
	} catch (IllegalArgumentException e) {
	    return "error";
	}
    }

    /** Shows an overview of all projects. */
    @GetMapping("/project_overview")
    public String showProjectOverview(Model model) {
//	model.addAttribute("projects", BackendAccessProvider.getAccessibleProjects(principal.getName()));
	model.addAttribute("projects", projects.findAll());

	// TODO: Debug
	/*for (Address a : addRepo.findAll()) {
	    /*System.out
		    .println("Users: " + r.getUsers().stream().map(u -> u.getUsername()).collect(Collectors.toList()));
	    System.out.println("List of Users is empty: " + r.getUsers().isEmpty());
	    System.out.println(
		    "Projects: " + r.getProjects().stream().map(p -> p.getName()).collect(Collectors.toList()));
	    System.out.println("List of projects is empty: " + r.getProjects().isEmpty());
	    if (a.getProject() != null) {
		System.out.println("Project of address: " + a.getProject().getName());
	    }
	}*/

	return "project/project_overview";

//	TODO get username via:
//	"@AuthenticationPrincipal User user" in method params, doesn't work yet
//	Working solutions:
//	"Principal principal" in method params
//	((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    // List of temp stati
    List<String> listOfStatus2 = List.of("OK", "OK", "NO_STATUS", "OPEN", "OPEN", "DENY", "OPEN", "OK", "OK", "OK",
	    "NO_STATUS", "OK", "OK", "OK", "OPEN", "OK", "OK", "DENY");

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
	    model.addAttribute("listOfStatus", listOfStatus2);
	    return "project/project_details";
	} catch (AuthenticationException | IllegalArgumentException e) {
	    return "error";
	}
    }
}