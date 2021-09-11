package com.lms2ue1.sbsweb.controller;

import java.security.Principal;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.backend.model.*;

@Controller
public class BillingItemController {

    @Autowired
    private BackendAccessProvider BAP;

    /** Shows the specified billing item's details. */
    @GetMapping("/project/{pID}/contract/{cID}/billing_item/{bID}/show")
    public String showBillingItemDetails(@PathVariable long pID, @PathVariable long cID, @PathVariable long bID,
	    Principal principal, Model model) {
	try {
	    String username = principal.getName();
	    model.addAttribute("pID", pID);
	    model.addAttribute("cID", cID);
	    model.addAttribute("bID", bID);
	    model.addAttribute("project", BAP.getProjectById(username, pID));
	    model.addAttribute("contract", BAP.getContractById(username, cID));
	    model.addAttribute("billingItem", BAP.getBillingItemById(username, bID));
	    return "billingitem/billing_item_details";
	} catch (AuthenticationException | IllegalArgumentException e) {
	    return "error";
	}
    }
}
