package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ContractController {

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
}