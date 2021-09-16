package com.lms2ue1.sbsweb.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple error controller for showing an error page when the user is not authorised to see a given page
 * @author Luca Anthony Schwarz (sunfl0w)
 */
@Controller
public class ErrorController {
    /**
     * Displays the unauthorised web page
     * @return URI of the unauthorised page
     */
    @GetMapping("/unauthorised")
    public String showUnauthorisedPage() {
        return "unauthorised";
    }
}
