package com.lms2ue1.sbsweb.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.model.Project;
import com.lms2ue1.sbsweb.model.User;

@Controller
public class ProjectController {

    /** Shows an overview of all projects. */
    @GetMapping("/project_overview")
    public String showProjectOverview(@AuthenticationPrincipal User user, Model model) {
//	model.addAttribute("projects", BackendAccessProvider.getAccessibleProjects(user.getUsername())); TODO
	model.addAttribute("projects", List.of(new Project("Nummer 1",0L), new Project("Hausbau",1L), new Project("Ende",2L)));
	return "project_overview";
    }

    /** Shows the specified project's details, e.g. its contracts. */
    @GetMapping("/project/{pID}/show")
    public String showProjectDetails(@PathVariable Long pID, Model model) {
	model.addAttribute("pID", pID);
	return "project_details";
    }
}