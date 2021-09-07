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

import com.lms2ue1.sbsweb.backend.model.Contract;
import com.lms2ue1.sbsweb.backend.model.Project;
import com.lms2ue1.sbsweb.backend.model.User;

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
//	model.addAttribute("projects", BackendAccessProvider.getAccessibleProjects(principal.getName()));
	model.addAttribute("projects",
		List.of(new Project(0, "Schule sanieren", null, null, null, null, 0, null, null, null, null, null, null,
			null, null),
			new Project(1, "Hausbau", "Haus an der Lindenallee 37 wird", null, null, null, 0, null, null,
				null, null, null, null, null, null),
			new Project(2, "Feierabend XTREME", null, null, null, null, 0, null, null, null, null, null,
				null, null, null)));
	return "project/project_overview";
//	TODO get username via:
//	"@AuthenticationPrincipal User user" in method params, doesn't work yet
//	Working solutions:
//	"Principal principal" in method params
//	((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    /** Shows the specified project's details, e.g. its contracts. */
    @GetMapping("/project/{pID}/show")
    public String showProjectDetails(@PathVariable Long pID, Model model) {
	model.addAttribute("pID", pID);
//	model.addAttribute("project", BackendAccessProvider.getProjectById(username, pID));
//	List<Contract> contracts = BackendAccessProvider.getAccessibleContracts(username);
//	model.addAttribute("contracts", contracts.stream().filter(contract -> contract.getPID() == pID).collect(Collectors.toList()));
	model.addAttribute("project", new Project(1, "Hausbau", "Haus an der Lindenallee 37 wird", null, null, null, 0,
		null, null, null, null, null, null, null, null));
	model.addAttribute("contracts", List.of(
		new Contract(0, "Wohnzimmer bauen", "Sachen müssen erledigt werden", null, null, null, null, null, null,
			null),
		new Contract(1, "Küche installieren", null, null, null, null, null, null, null, null),
		new Contract(2, "Baby beruhigen", null, null, null, null, null, null, null, null),
		new Contract(3, "Kosten klein halten", null, null, null, null, null, null, null, null)));
	return "project/project_details";
    }
}