package com.lms2ue1.sbsweb.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lms2ue1.sbsweb.backend.restapi.JSONDeserialiser;

@Controller
public class LoginController {
    
    @Autowired
    JSONDeserialiser jDes;

    /** Shows the login page. 
     * @throws IOException 
     * @throws JsonProcessingException 
     * @throws JsonMappingException */
    @GetMapping("/login")
    public String showLogin(HttpSession session, Model model) throws JsonMappingException, JsonProcessingException, IOException {
        // Set darkmode to false at login so there is a value present
        session.setAttribute("darkmodeEnabled", false);
        
        jDes.deserialiseProjects();
        
        return "login";
    }
}