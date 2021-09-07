package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectController {

    /** Shows an overview of all projects. */
    @GetMapping("/project_overview")
    public String showProjectOverview(Model model) {
	// TODO
	return "project/project_overview";
    }

    /** Shows the specified project's details, e.g. its contracts. */
    @GetMapping("/project/{pID}/show")
    public String showProjectDetails(@PathVariable Long pID, Model model) {
	// TODO
	return "project/project_details";
    }
}