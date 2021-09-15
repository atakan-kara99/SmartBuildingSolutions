package com.lms2ue1.sbsweb.controller;

import java.security.Principal;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.repository.BillingItemRepository;
import com.lms2ue1.sbsweb.backend.security.AuthorisationCheck;

@Controller
public class BillingItemController {

    @Autowired
    private BackendAccessProvider BAP;
    @Autowired
    private AuthorisationCheck auth;
    @Autowired
    private BillingItemRepository bs;

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
	    model.addAttribute("admin", auth.isAdmin(username));
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
	    if (auth.isAdmin(username)) {
		model.addAttribute("pID", pID);
		model.addAttribute("cID", cID);
		model.addAttribute("project", BAP.getProjectById(username, pID));
		Contract contract = BAP.getContractById(username, cID);
		model.addAttribute("contract", contract);
		model.addAttribute("billingItem", new BillingItem());
		model.addAttribute("billingUnits", contract.getBillingUnits());
		return "billingitem/billing_item_new";
	    } else {
		throw new AuthenticationException();
	    }
	} catch (AuthenticationException | IllegalArgumentException e) {
	    return "error";
	}
    }

    /** Tries to save the new billing item. */
    @PostMapping("/project/{pID}/contract/{cID}/billing_item_save")
    public String addNewBillingItem(@PathVariable long pID, @PathVariable long cID, @Valid BillingItem billingItem,
	    BindingResult bindingResult, Principal principal, Model model) {
	if (bindingResult.hasErrors()) {
	    model.addAttribute("billingItem", billingItem);
	    return "billingitem/billing_item_new";
	}
	// Initial status
//	billingItem.setStatus(); TODO

	// TODO save using BAP once it's implemented
	try {
	    String username = principal.getName();
	    BAP.addBillingItem(username, billingItem);
	} catch (AuthenticationException | IllegalArgumentException e) {
	    model.addAttribute("billingItem", billingItem);
	    return "billingitem/billing_item_new";
	}
	return "redirect:/project/{pID}/contract/{cID}/show";
    }

    /** Updates a billing item's status. 
     * @throws AuthenticationException */
    @PostMapping("/project/{pID}/contract/{cID}/billing_item/{bID}/update_status")
    public String updateBillingItemStatus(@PathVariable long pID, @PathVariable long cID, @PathVariable long bID,
	    @Valid Status status, BindingResult bindingResult, Principal principal, Model model) throws AuthenticationException {
	if (bindingResult.hasErrors()) {
	    return "error";
	}
	String username = principal.getName();
	BAP.updateBillingItemStatus(username, bID, status);

	return "redirect:/project/{pID}/contract/{cID}/billing_item/{bID}/show";
    }
}
