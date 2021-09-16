package com.lms2ue1.sbsweb.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lms2ue1.sbsweb.backend.model.BackendAccessProvider;
import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Role;
import com.lms2ue1.sbsweb.backend.model.User;

/**
 * Controller for handeling everything related to user management.
 * 
 * @author Luca Anthony Schwarz (sunfl0w)
 */
@Controller
public class UserManagementController {
    @Autowired
    BackendAccessProvider backendAccessProvider;

    /**
     * Shows all accessible users of the Organisation specified by oID. For each user the username and the role is shown. Two options are also displayed: edit
     * and delete. A user is not able to delete him or herself.
     * 
     * @param  principal Security principal to access current user's name
     * @param  oID       ID of the relevant organisation
     * @param  model     Spring model to provide instances to the web page
     * @return           URI of the HTML user management page or the unauthorised page
     */
    @GetMapping("/organisation/{oID}/user_management")
    public String showUserList(Principal principal, @PathVariable Long oID, Model model) {
        Organisation organisation = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        List<Role> roles = organisation.getRoles();
        List<User> users = new ArrayList<User>();
        for (Role role : roles) {
            users.addAll(role.getUsers());
        }
        model.addAttribute("users", users);
        model.addAttribute("organisation", organisation);
        return "user/user_management";
    }

    /**
     * Displays new user page on which a new user can be created for the organisation.
     * 
     * @param  principal Security principal to access current user's name
     * @param  oID       ID of the relevant organisation
     * @param  model     Spring model to provide instances to the web page
     * @return           URI of the HTML user new page or the unauthorised page
     */
    @GetMapping("/organisation/{oID}/user_management/user_new")
    public String showNewUserForm(Principal principal, @PathVariable Long oID, Model model) {
        Organisation organisation = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        model.addAttribute("user", new User());
        model.addAttribute("organisation", organisation);
        return "user/user_new";
    }

    /**
     * POST mapping to add a new user to the relevant organisation.
     * 
     * @param  principal     Security principal to access current user's name
     * @param  oID           ID of the relevant organisation
     * @param  user          The User instance to add
     * @param  bindingResult Binding results used for error checking
     * @param  model         Spring model to provide instances to the web page
     * @return               Redirect to user management or the unauthorised page
     */
    @PostMapping("/organisation/{oID}/user_management/user_save")
    public String addNewUser(Principal principal, @PathVariable Long oID, @Valid User user, BindingResult bindingResult, Model model) {
        Organisation organisation = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("organisation", organisation);
            return "user/user_new";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User newUser = new User(user.getForename(), user.getLastname(), user.getRole(), user.getUsername(), encoder.encode(user.getPassword()));
        try {
            backendAccessProvider.addUser(principal.getName(), newUser);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        return "redirect:/organisation/{oID}/user_management";
    }

    /**
     * GET mapping to delete a user specified by uID of the relevant organisation.
     * 
     * @param  principal Security principal to access current user's name
     * @param  oID       ID of the relevant organisation
     * @param  uID       ID of the relevant user
     * @return           Redirect to user management or the unauthorised page
     */
    @GetMapping("/organisation/{oID}/user_management/user/{uID}/user_delete")
    public String deleteUserById(Principal principal, @PathVariable Long oID, @PathVariable Long uID) {
        try {
            backendAccessProvider.removeUser(principal.getName(), uID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        return "redirect:/organisation/{oID}/user_management";
    }

    /**
     * Shows the user edit page of the specified user in the relevant organisation.
     * 
     * @param  principal Security principal to access current user's name
     * @param  oID       ID of the relevant organisation
     * @param  uID       ID of the relevant user
     * @param  model     Spring model to provide instances to the web page
     * @return           URI of the HTML user edit page or the unauthorised page
     */
    @GetMapping("/organisation/{oID}/user_management/user/{uID}/user_edit")
    public String showUserEditById(Principal principal, @PathVariable Long oID, @PathVariable Long uID, Model model) {
        Organisation organisation = null;
        User user = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
            user = backendAccessProvider.getUserById(principal.getName(), uID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        model.addAttribute("user", user);
        model.addAttribute("organisation", organisation);
        return "user/user_edit";
    }

    /**
     * POST mapping to update user data of the specified user and relevant organisation.
     * 
     * @param  principal     Security principal to access current user's name
     * @param  oID           ID of the relevant organisation
     * @param  uID           ID of the relevant user
     * @param  user          User instance with the updated data
     * @param  bindingResult Binding results used for error checking
     * @param  model         Spring model to provide instances to the web page
     * @return               Redirect to user management or the unauthorised page
     */
    @PostMapping("/organisation/{oID}/user_management/user/{uID}/user_update")
    public String editUserById(Principal principal, @PathVariable Long oID, @PathVariable Long uID, @Valid User user, BindingResult bindingResult,
    Model model) {
        Organisation organisation = null;
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("organisation", organisation);
            return "user/user_edit";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User updatedUser = new User(user.getForename(), user.getLastname(), user.getRole(), user.getUsername(), encoder.encode(user.getPassword()));
        try {
            backendAccessProvider.updateUser(principal.getName(), user.getId(), updatedUser);
        } catch (AuthenticationException authException) {
            return "redirect:/unauthorised";
        }
        return "redirect:/organisation/{oID}/user_management";
    }
}