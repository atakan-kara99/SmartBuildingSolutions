package com.lms2ue1.sbsweb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomePageController {


    @GetMapping("/")
    public String showDefaultPage(Model model) {
        return "redirect:/index";
    }
    /**
     * Shows the homepage.
     */
    @GetMapping("/index")
    public String showHomePage(Model model) {
        return "index";
    }

}
