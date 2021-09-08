package com.lms2ue1.sbsweb.controller;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lms2ue1.sbsweb.backend.model.BillingItem;
import com.lms2ue1.sbsweb.backend.model.Contract;
import com.lms2ue1.sbsweb.backend.model.Status;
import com.lms2ue1.sbsweb.backend.repository.BillingItemRepository;
import com.lms2ue1.sbsweb.backend.repository.ContractRepository;

@Controller
public class ContractController {

    @Autowired
    ContractRepository contracts;
    @Autowired
    BillingItemRepository billingItems;

    /** Shows the specified contract's details, e.g. its billing items. */
    @GetMapping("/project/{pID}/contract/{cID}/show")
    public String showContractDetails(@PathVariable Long pID, @PathVariable Long cID, Model model) {
	model.addAttribute("pID", pID);
	model.addAttribute("cID", cID);
//	model.addAttribute("contract", BackendAccessProvider.getContractById(username, cID));
//	List<BillingItem> billingItems = BackendAccessProvider.getAccessibleBillingItems(username);
//	model.addAttribute("billingItems", billingItems.stream().filter(billingItem -> billingItem.getCID() == cID).collect(Collectors.toList()));
	model.addAttribute("contract", contracts.findById(cID));
	model.addAttribute("billingItems", StreamSupport.stream(billingItems.findAll().spliterator(), false)
		.filter(billingItem -> billingItem.getBillingUnit().getContract().getOrganisationId() == cID));
	return "contract/contract_details";
    }
}
