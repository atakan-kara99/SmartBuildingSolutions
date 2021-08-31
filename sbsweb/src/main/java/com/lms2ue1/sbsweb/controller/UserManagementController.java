package com.lms2ue1.sbsweb.controller;

import com.lms2ue1.sbsweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserManagementController {
    @Autowired
    UserRepository userRepository;

    // How should we handle the home page / ? This will redirect to user management for now
    @GetMapping("/")
    public String showUserManagementRedirect(Model model) {
        model.addAttribute("message", "Testnachricht");
        return "redirect:userManagement";
    }

    @GetMapping("/userManagement")
    public String showUserManagement(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user_management";
    }
}