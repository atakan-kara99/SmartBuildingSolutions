package com.lms2ue1.sbsweb.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.lms2ue1.sbsweb.backend.jwt.JWTRequest;
import com.lms2ue1.sbsweb.backend.jwt.JWTResponse;
import com.lms2ue1.sbsweb.backend.model.BackendAccessProvider;
import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Project;
import com.lms2ue1.sbsweb.backend.model.Status;
import com.lms2ue1.sbsweb.backend.model.User;
import com.lms2ue1.sbsweb.backend.security.SBSUserDetails;
import com.lms2ue1.sbsweb.service.DBUserDetailsService;
import com.lms2ue1.sbsweb.service.JWTSecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.SignatureException;

/**
 * Controller that represents the entire REST-API used by the app to communicate with the server
 */
@RestController
public class AppAPIController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DBUserDetailsService userDetailsService;

    @Autowired
    private JWTSecurityService jwtSecurityService;

    @Autowired
    private BackendAccessProvider backendAccessProvider;

    /**
     * Post mapping to the authentication used by the REST-API. Used for getting a jwt if the provided username and password in plain text are valid
     * 
     * @param  authRequest The provided authentication request
     * @return             If the authentication was successful then a valid jwt is returned. Else an Http error status will be returned
     */
    @PostMapping("/api/auth")
    public ResponseEntity<?> createJWTToken(@RequestBody JWTRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        SBSUserDetails userDetails = (SBSUserDetails) userDetailsService.loadUserByUsername(authRequest.getUsername());
        String jwtToken = jwtSecurityService.generateToken(userDetails);
        return ResponseEntity.ok(new JWTResponse(jwtToken));
    }

    /**
     * Get mapping to recieve all projects the requester should have access to
     * 
     * @param  requestHeader The request
     * @return               All projects the requester has access to or an error if the jwt is invalid
     */
    @GetMapping("/api/projects")
    public List<Project> getProjects(@RequestHeader(name = "Authorization") String requestHeader) {
        try {
            String username = jwtSecurityService.getUsername(requestHeader.substring(7));
            return backendAccessProvider.getAllProjects(username);
        } catch (SignatureException signatureException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied", signatureException);
        }
    }

    /**
     * Get mapping to recieve the user data of the requester
     * 
     * @param  requestHeader The request
     * @return               User data of the requester or an error if the jwt is invalid
     */
    @GetMapping("/api/user")
    public User getUser(@RequestHeader(name = "Authorization") String requestHeader) {
        try {
            String username = jwtSecurityService.getUsername(requestHeader.substring(7));
            User user = backendAccessProvider.getAllUsers(username).get(0);
            // Only use necessary data
            return new User(user.getForename(), user.getLastname(), null, user.getUsername(), null);
        } catch (SignatureException signatureException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied", signatureException);
        }
    }

    /**
     * Get mapping to recieve all organisations the requester should have access to
     * 
     * @param  requestHeader The request
     * @return               All organisations the requester has access to or an error if the jwt is invalid
     */
    @GetMapping("/api/organisations")
    public List<Organisation> getOrganisation(@RequestHeader(name = "Authorization") String requestHeader) {
        try {
            String username = jwtSecurityService.getUsername(requestHeader.substring(7));
            List<Organisation> organisations = backendAccessProvider.getAllOrganisations(username);
            List<Organisation> organisationsToSend = new ArrayList<Organisation>();
            for (Organisation organisation : organisations) {
                // Only use necessary data
                organisationsToSend.add(new Organisation(organisation.getName()));
            }
            return organisationsToSend;
        } catch (SignatureException signatureException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied", signatureException);
        }
    }

    /**
     * Post mapping to update the status of a billing unit by its id
     * 
     * @param  requestHeader The request header
     * @param  statusUpdate  The status update object containing a billing item id and a status
     * @return               OK message if the status was successfully changed
     */
    @PostMapping("/api/update_status")
    public ResponseEntity<?> updateBillingItemStatus(@RequestHeader(name = "Authorization") String requestHeader, @Valid StatusUpdate statusUpdate) {
        try {
            String username = jwtSecurityService.getUsername(requestHeader.substring(7));
            backendAccessProvider.updateStatus(username, statusUpdate.id, statusUpdate.status);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied", exception);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // TODO: Implement properly. This will not work!
    class StatusUpdate {
        private long id;
        @NotEmpty
        private Status status;

        StatusUpdate() {
        }
    }
}