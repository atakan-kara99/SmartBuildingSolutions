package com.lms2ue1.sbsweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.h2.util.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller class controlling the styling of the web page. Currently there is only support for toggling dark and light modes.
 *
 * @author Luca Anthony Schwarz (sunfl0w)
 */
@Controller
public class StyleController {
    /**
     * POST mapping for styling requests. Will redirect to previous page after styling was saved in session attributes
     * 
     * @param request HttpServletRequest
     * @param session HTTP session
     * @param requestBody POST request body
     * @return Redirect to previous web page
     */
    @PostMapping("/set_style")
    public String setDarkmode(HttpServletRequest request, HttpSession session, @RequestBody String requestBody) {
        JSONObject requestObject = new JSONObject(requestBody);
        Boolean darkmodeEnabled = (Boolean)requestObject.get("darkmodeEnabled");
        session.setAttribute("darkmodeEnabled", darkmodeEnabled);
        return "redirect:" + request.getHeader("Referer");
    }
}