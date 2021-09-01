package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ContractController {

    //// CONTRACTS ////

    /** Shows an overview of all contracts. */
    @GetMapping("/contract_overview")
    public String showContractOverview(Model model) {
	return "contract_overview";
    }

    /** Shows the specified contract's details, e.g. its billing items. */
    @GetMapping("/project/{pID}/contract/{cID}/show")
    public String showContractDetails(@PathVariable Long pID, @PathVariable Long cID, Model model) {
	// TODO
	return "contract_details";
    }

    //// DIAGRAMS ////

    /** Shows an overview of all diagrams. */
    @GetMapping("/diagram_overview")
    public String showDiagramOverview(Model model) {
	// TODO
	return "diagram_overview";
    }

    /** Shows the page to create a diagram for the given contract. */
    @GetMapping("/project/{pID}/contract/{cID}/create_diagram")
    public String showCreateDiagram(@PathVariable Long pID, @PathVariable Long cID, Model model) {
	// TODO the created diagram should have an id,
	// automatically redirect to the page showing the diagram
	return "create_diagram";
    }

    /** Shows a page displaying the specified diagram. */
    @GetMapping("/project/{pID}/contract/{cID}/diagram/{diaID}/show")
    public String showDiagram(@PathVariable Long pID, @PathVariable Long cID, @PathVariable Long diaID, Model model) {
	// TODO
	return "diagram_details";
    }

    /** Shows an overview of all diagrams satisfying the filter criteria. */
    @GetMapping("/diagram_overview/filter/TODO")
    public String showDiagramFilter(Model model) {
	// TODO, low priority (Kannkriterium)
	// Not sure how to apply the filter yet (using path arguments?)
	return null;
    }
}