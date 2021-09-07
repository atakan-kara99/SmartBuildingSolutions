package com.lms2ue1.sbsweb.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.backend.model.BillingItem;
import com.lms2ue1.sbsweb.backend.model.Contract;

@Controller
public class ContractController {

    /** Shows the specified contract's details, e.g. its billing items. */
    @GetMapping("/project/{pID}/contract/{cID}/show")
    public String showContractDetails(@PathVariable Long pID, @PathVariable Long cID, Model model) {
	model.addAttribute("pID", pID);
	model.addAttribute("cID", cID);
//	model.addAttribute("contract", BackendAccessProvider.getContractById(username, cID));
//	List<BillingItem> billingItems = BackendAccessProvider.getAccessibleBillingItems(username);
//	model.addAttribute("billingItems", billingItems.stream().filter(billingItem -> billingItem.getCID() == cID).collect(Collectors.toList()));
	model.addAttribute("contract", new Contract(0, "Wohnzimmer bauen", "Sachen müssen erledigt werden", null, null,
		null, null, null, null, null));
	model.addAttribute("billingItems",
		List.of(new BillingItem(0, 0, "Heizkörper B7-2 fensternah einbauen.", "OPEN", 0, null, 0, null, null,
			null, null, null),
			new BillingItem(0, 0, "Fenster einbauen", null, 0, null, 0, null, null, null, null, null)));
	return "contract/contract_details";
    }
}
