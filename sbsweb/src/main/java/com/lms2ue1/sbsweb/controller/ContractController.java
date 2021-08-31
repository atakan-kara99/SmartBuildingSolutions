package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContractController {
	
    @GetMapping("/contracts")
    public String showContractOverview(Model model) {
        return "contract_overview";
    }

    @GetMapping("/createDiagram")
    public String showCreateDiagram(Model model) {
        return "create_Diagram";
    }
    
    @GetMapping("/diagram")
    public String showDiagram(Model model) {
        return "diagram";
    }
    
    @GetMapping("/filter")
    public String showFilter(Model model) {
        return "filter";
    }
}