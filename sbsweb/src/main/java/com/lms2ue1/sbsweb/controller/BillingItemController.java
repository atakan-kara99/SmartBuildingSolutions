package com.lms2ue1.sbsweb.controller;

import java.security.Principal;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.backend.model.*;

@Controller
public class BillingItemController {

    @Autowired
    private BackendAccessProvider BAP;

    /**
     * Shows the specified billing item's details and potential nested billing
     * items.
     */
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

    /** Shows the form to create a new billing item. */
    @GetMapping("/project/{pID}/contract/{cID}/billing_item_new")
    public String showNewBillingItemForm(@PathVariable long pID, @PathVariable long cID, Principal principal,
	    Model model) {
	try {
	    String username = principal.getName();
	    model.addAttribute("pID", pID);
	    model.addAttribute("cID", cID);
	    model.addAttribute("project", BAP.getProjectById(username, pID));
	    model.addAttribute("contract", BAP.getContractById(username, cID));
	    return "billingitem/billing_item_new";
	} catch (AuthenticationException | IllegalArgumentException e) {
	    return "error";
	}
    }

    /** Shows the form to create a new billing item. */
    @GetMapping("/project/{pID}/contract/{cID}/billing_item_save")
    public String addNewBillingItem(@PathVariable long pID, @PathVariable long cID, @Valid BillingItem billingItem,
	    BindingResult bindingResult, Principal principal, Model model) {
	if (bindingResult.hasErrors()) {
	    model.addAttribute("billingItem", billingItem);
	    return "billingitem/billing_item_new";
	}

	try {
	    String username = principal.getName();
	    model.addAttribute("pID", pID);
	    model.addAttribute("cID", cID);
	    model.addAttribute("project", BAP.getProjectById(username, pID));
	    model.addAttribute("contract", BAP.getContractById(username, cID));
	    return "billingitem/billing_item_new";
	} catch (AuthenticationException | IllegalArgumentException e) {
	    e.printStackTrace();
	}
	return "redirect:/project/{pID}/contract/{cID}/show";
    }
}
