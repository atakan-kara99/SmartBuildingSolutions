package com.lms2ue1.sbsweb.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.backend.model.*;

@Controller
public class ContractController {

    @Autowired
    BackendAccessProvider BAP;

    /** Shows the specified contract's details, e.g. its billing items. */
    @GetMapping("/project/{pID}/contract/{cID}/show")
    public String showContractDetails(@PathVariable long pID, @PathVariable long cID, Principal principal,
	    Model model) {
	try {
	    String username = principal.getName();
	    model.addAttribute("pID", pID);
	    model.addAttribute("cID", cID);
	    model.addAttribute("contract", BAP.getContractById(username, cID));
	    List<BillingItem> billingItems = BAP.getAllBillingItems(username);
	    model.addAttribute("billingItems",
		    billingItems.stream()
			    .filter(billingItem -> billingItem.getBillingUnit().getContract().getId() == cID)
			    .collect(Collectors.toList()));
	    return "contract/contract_details";
	} catch (AuthenticationException | IllegalArgumentException e) {
	    return "error";
	}
    }
}
