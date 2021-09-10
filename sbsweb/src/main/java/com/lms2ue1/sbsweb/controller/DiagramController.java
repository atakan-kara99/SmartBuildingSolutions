package com.lms2ue1.sbsweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.backend.model.Address;
import com.lms2ue1.sbsweb.backend.model.Contract;
import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Project;
import com.lms2ue1.sbsweb.backend.model.Status;
import com.lms2ue1.sbsweb.backend.repository.ContractRepository;
import com.lms2ue1.sbsweb.backend.repository.ProjectRepository;

@Controller
public class DiagramController {
	
	@Autowired
	ContractRepository conRepo;
	
	@Autowired
	ProjectRepository proRepo;
	

	//TODO: Delete later
	
	List<String> labels = List.of("TEST", "NO_STATUS","OPEN","OK","DENY");
	
	List<String> listOfStatus = List.of("OK","OK","NO_STATUS","OPEN","OPEN","NO_STATUS","DENY","OPEN","OK","OK","OK","NO_STATUS","OK","OK","OK","OPEN","OK","OK","DENY","DENY");
	
	

    /** Shows an overview of all project's diagrams. */
    @GetMapping("/project/{pID}/diagrams")
    public String showProjectDiagramOverview(@PathVariable Long pID, Model model) {
    	
    	model.addAttribute("project", proRepo.findById(pID).get());
    	model.addAttribute("projectName", "Projekt A");
    	model.addAttribute("labels", labels);
    	model.addAttribute("listOfStatus",listOfStatus);
    	
    	return "diagram/project_diagram_overview";
    }

    /** Shows an overview of all contract's diagrams. */
    @GetMapping("/project/{pID}/contract/{cID}/diagrams")
    public String showContractDiagramOverview(@PathVariable Long pID, @PathVariable Long cID, Model model) {

    	model.addAttribute("contract", conRepo.findById(cID).get());
		model.addAttribute("contractName", "Vertrag A");
    	model.addAttribute("labels", labels);
    	model.addAttribute("listOfStatus",listOfStatus);
		return "diagram/contract_diagram_overview";
    }
}
