package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BillingItemController {

    /** Shows an overview of all billing items. */
    @GetMapping("/billing_item_overview")
    public String showBillingItemOverview(Model model) {
	return "billingitem/billing_item_overview";
    }

    /** Shows the specified billing item's details. */
    @GetMapping("/project/{pID}/contract/{cID}/billing_item/{bID}/show")
    public String showBillingItemDetails(@PathVariable Long pID, @PathVariable Long cID, @PathVariable Long bID,
	    Model model) {
	// TODO
	return "billingitem/billing_item_details";
    }
}
