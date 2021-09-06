package com.lms2ue1.sbsweb.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.model.BillingItem;
import com.lms2ue1.sbsweb.model.User;

@Controller
public class BillingItemController {

    /** Shows the specified billing item's details. */
    @GetMapping("/project/{pID}/contract/{cID}/billing_item/{bID}/show")
    public String showBillingItemDetails(@PathVariable Long pID, @PathVariable Long cID, @PathVariable Long bID,
	    Model model) {
	model.addAttribute("pID", pID);
	model.addAttribute("cID", cID);
	model.addAttribute("bID", bID);
//	model.addAttribute("billingItem", BackendAccessProvider.getBillingItemById(username, bID));
	model.addAttribute("billingItem", new BillingItem("Heizung montieren", 0L, "Heizkörper B7-2 fensternah einbauen.", "OPEN"));
	return "billing_item_details";
    }
}
