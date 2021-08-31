package com.lms2ue1.sbsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Login_Controller {
    @GetMapping("/login")
    public String showLogin(Model model) {
        return "login";
    }
}