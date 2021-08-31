package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserManagementController {
    // How shouöd we handle the home page / ? This will redirect to user management for now
    @GetMapping("/")
    public String showUserManagementRedirect(Model model) {
        model.addAttribute("message", "Testnachricht");
        return "redirect:userManagement";
    }

    @GetMapping("/userManagement")
    public String showUserManagement(Model model) {
        model.addAttribute("message", "Testnachricht");
        return "user_management";
    }
}