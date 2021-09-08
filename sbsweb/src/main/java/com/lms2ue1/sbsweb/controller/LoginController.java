package com.lms2ue1.sbsweb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /** Shows the login page. */
    @GetMapping("/login")
    public String showLogin(HttpSession session, Model model) {
        // Set darkmode to false at login so there is a value present
        session.setAttribute("darkmodeEnabled", false);
        return "login";
    }
}