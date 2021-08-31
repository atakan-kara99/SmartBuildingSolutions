package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectOverview {
	
    @GetMapping("/projects")
    public String showProjectOverview(Model model) {
        return "project_overview";
    }
    
    @GetMapping("/billingItemsOverview")
    public String showBillingItemOverview(Model model) {
    	return "billing_item_overview";
    }
    
    //GetMapping ist hier falsch, aber für Dummy genügt es erstmal
    @GetMapping("/billingItemXYZ")
    public String showBillingItemDetails(Model model) {
    	return "billing_item";
    }
}