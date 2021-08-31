package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BillingItemController {
    
    @GetMapping("/billing_Items_Overview")
    public String showBillingItemOverview(Model model) {
    	return "billing_item_overview";
    }
    
    //GetMapping ist hier falsch, aber für Dummy genügt es erstmal
    @GetMapping("/billing_Item_XYZ")
    public String showBillingItemDetails(Model model) {
    	return "billing_item_XYS";
    }
}
