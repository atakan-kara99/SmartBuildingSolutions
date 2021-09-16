package com.lms2ue1.sbsweb.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.security.AuthorisationCheck;

@Controller
public class ContractController {

    @Autowired
    private BackendAccessProvider BAP;
    @Autowired
    private AuthorisationCheck auth;

    // List of temp stati for details
    List<String> listOfStatus = List.of("OK", "OK", "NO_STATUS", "OPEN", "OPEN", "DENY", "OPEN", "OK", "OK", "OK",
	    "NO_STATUS", "OK", "OK", "OK", "OPEN", "OK", "OK", "DENY");
    
    //List of list of status
    List<List<String>> listOfListOfStatus = List.of(listOfStatus,listOfStatus,listOfStatus,listOfStatus,listOfStatus,listOfStatus);

    /** Shows the specified contract's details, e.g. its billing items. */
    @GetMapping("/project/{pID}/contract/{cID}/show")
    public String showContractDetails(@PathVariable long pID, @PathVariable long cID, Principal principal,
	    Model model) {
	try {
	    String username = principal.getName();
	    model.addAttribute("admin", auth.isAdmin(username));
	    model.addAttribute("pID", pID);
	    model.addAttribute("cID", cID);
	    model.addAttribute("project", BAP.getProjectById(username, pID));
	    model.addAttribute("contract", BAP.getContractById(username, cID));
	    List<BillingItem> billingItems = BAP.getAllBillingItems(username).stream()
		    .filter(b -> b.getBillingUnit().getContract().getId() == cID)
		    .collect(Collectors.toCollection(ArrayList::new));
	    model.addAttribute("listOfListOfStatus", listOfListOfStatus);
	    // Flattened list, keep only high level billing items
	    List<Integer> removes = new ArrayList<>(billingItems.size());
	    for (int i = 0; i < billingItems.size(); i++) {
		BillingItem bill = billingItems.get(i);
		for (int j = 0; j < billingItems.size(); j++) {
		    if (j != i) {
			long bID = billingItems.get(j).getId();
			if (bill.getBillingItems().stream().anyMatch(b -> b.getId() == bID)) {
			    removes.add(j);
			}
		    }
		}
	    }
	    for (int i = 0; i < removes.size(); i++) {
		billingItems.remove((int) removes.get(i));
	    }
	    model.addAttribute("billingItems", billingItems);
	    return "contract/contract_details";
	} catch (AuthenticationException | IllegalArgumentException e) {
	    return "error";
	}
    }
}
