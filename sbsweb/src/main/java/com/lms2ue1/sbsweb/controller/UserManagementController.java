package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserManagementController {
    @GetMapping("/userManagement")
    public String showUserManagement(Model model) {
        model.addAttribute("message", "Testnachricht");
        return "user_management";
    }
}