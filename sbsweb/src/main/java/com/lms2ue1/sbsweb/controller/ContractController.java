package com.lms2ue1.sbsweb.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.model.BillingItem;
import com.lms2ue1.sbsweb.model.Contract;
import com.lms2ue1.sbsweb.model.Project;
import com.lms2ue1.sbsweb.model.User;

@Controller
public class ContractController {

    /** Shows the specified contract's details, e.g. its billing items. */
    @GetMapping("/project/{pID}/contract/{cID}/show")
    public String showContractDetails(@PathVariable Long pID, @PathVariable Long cID, Model model) {
	model.addAttribute("pID", pID);
	model.addAttribute("cID", cID);
//	model.addAttribute("contract", BackendAccessProvider.getContractById(cID));
//	List<BillingItem> billingItems = BackendAccessProvider.getAccessibleBillingItems(user.getUsername());
//	model.addAttribute("billingItems", billingItems.stream().filter(billingItem -> billingItem.getCID() == cID).collect(Collectors.toList()));
	model.addAttribute("contract", new Contract("Wohnzimmer bauen", 0L));
	model.addAttribute("billingItems",
		List.of(new BillingItem("Heizung montieren", 0L), new BillingItem("Fenster einbauen", 1L)));
	return "contract_details";
    }
}