package com.lms2ue1.sbsweb.backend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lms2ue1.sbsweb.service.DBUserDetailsService;
import com.lms2ue1.sbsweb.service.JWTSecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Request filter used in the REST-API configuration used by the App. Checks validity of jwt tokens
 * @author Luca Anthony Schwarz (sunfl0w)
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    private DBUserDetailsService userDetailsService;

    @Autowired
    private JWTSecurityService jwtSecurityService;

    /**
     * Override of doFilterInternal to check request validity and token validity. After that the next filter is executed.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        
        // Catchingg exceptions when trying to check validity. This will only print the stack trace and the error is actually managed in the AppAPIController
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7);
                username = jwtSecurityService.getUsername(jwtToken);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                SBSUserDetails userDetails = (SBSUserDetails) userDetailsService.loadUserByUsername(username);
                if (jwtSecurityService.checkTokenValidity(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        chain.doFilter(request, response);
    }
}