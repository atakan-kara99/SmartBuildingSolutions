package com.lms2ue1.sbsweb.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.model.Contract;
import com.lms2ue1.sbsweb.model.Project;
import com.lms2ue1.sbsweb.model.User;

@Controller
public class ProjectController {

    // Going to a billing item:
    // 1. Select a project from project_overview
    // 2. Select a contract from project_details
    // 3. Select a billing item from contract_details
    // 4. Success: billing_item_details

    /** Shows an overview of all projects. */
    @GetMapping("/project_overview")
    public String showProjectOverview(Model model) {
//	model.addAttribute("projects", BackendAccessProvider.getAccessibleProjects(user.getUsername()));
	model.addAttribute("projects", List.of(new Project("Schule sanieren", 0L), new Project("Hausbau", 1L),
		new Project("Feierabend XTREME", 2L)));
	return "project_overview";
//	TODO get username via:
//	"@AuthenticationPrincipal User user" in method params, doesn't work yet
//	Working solutions:
//	"Principal principal" in method params
//	System.out.println(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    /** Shows the specified project's details, e.g. its contracts. */
    @GetMapping("/project/{pID}/show")
    public String showProjectDetails(@PathVariable Long pID, Model model) {
	model.addAttribute("pID", pID);
//	model.addAttribute("project", BackendAccessProvider.getProjectById(pID));
//	List<Contract> contracts = BackendAccessProvider.getAccessibleContracts(user.getUsername());
//	model.addAttribute("contracts", contracts.stream().filter(contract -> contract.getPID() == pID).collect(Collectors.toList()));
	model.addAttribute("project", new Project("Hausbau", 1L));
	model.addAttribute("contracts",
		List.of(new Contract("Wohnzimmer bauen", 0L), new Contract("Küche installieren", 1L),
			new Contract("Baby beruhigen", 2L), new Contract("Kosten klein halten", 3L)));
	return "project_details";
    }
}