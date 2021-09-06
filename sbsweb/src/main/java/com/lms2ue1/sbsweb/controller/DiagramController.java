package com.lms2ue1.sbsweb.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class DiagramController {

    /** Shows an overview of all project's diagrams. */
    @GetMapping("/project_diagram_overview")
    public String showProjectDiagramOverview(Model model) {
	// TODO
	return "project_diagram_overview";
    }

    /** Shows an overview of all contract's diagrams. */
    @GetMapping("/contract_diagram_overview")
    public String showContractDiagramOverview(Model model) {
	// TODO
	return "contract_diagram_overview";
    }
}
