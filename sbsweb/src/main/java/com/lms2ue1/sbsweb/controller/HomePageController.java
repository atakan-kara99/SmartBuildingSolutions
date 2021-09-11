package com.lms2ue1.sbsweb.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class HomePageController {

    /** Shows the homepage. */
    @GetMapping("/homepage")
    public String showHomePage(Model model) {
        return "homepage";
    }

}
