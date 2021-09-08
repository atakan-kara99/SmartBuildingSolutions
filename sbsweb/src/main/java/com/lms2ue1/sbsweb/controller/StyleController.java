package com.lms2ue1.sbsweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class StyleController {
    @PostMapping("/set_style")
    public String setDarkmode(HttpServletRequest request, HttpSession session, @RequestBody String requestBody) {
        JSONObject requestObject = new JSONObject(requestBody);
        Boolean darkmodeEnabled = (Boolean)requestObject.get("darkmodeEnabled");
        session.setAttribute("darkmodeEnabled", darkmodeEnabled);
        System.out.println("----");
        System.out.println("1" + darkmodeEnabled);
        System.out.println("2" + session.getAttribute("darkmodeEnabled"));
        System.out.println("----");
        return "redirect:" + request.getHeader("Referer");
    }
}