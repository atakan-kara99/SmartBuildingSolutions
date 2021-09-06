package com.lms2ue1.sbsweb.controller;

import javax.validation.Valid;

import com.lms2ue1.sbsweb.model.Organisation;
import com.lms2ue1.sbsweb.model.User;
import com.lms2ue1.sbsweb.repository.OrganisationRepository;
import com.lms2ue1.sbsweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserManagementController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    // Note! We will need a proper backend so all of this works properly!

    // This redirect will not work as of now. We will need the backend for this to work properly
    @GetMapping("/")
    public String showUserManagementRedirect(Model model) {
        model.addAttribute("message", "Testnachricht");
        return "redirect:/organisation/4/user_management";
    }

    /** Shows an overview of <b> ALL </b> organisations' users. */
    @GetMapping("/organisation/{oID}/user_management")
    public String showUserList(@PathVariable Long oID, Model model) {
        model.addAttribute("users", userRepository.findAll());
        Organisation orga = organisationRepository.findById(oID).get();
        model.addAttribute("organisation", orga);
        return "user_management";
    }

    /** Shows the page to add a new user to an organisation. */
    @GetMapping("/organisation/{oID}/user_management/user_new")
    public String showNewUserForm(@PathVariable Long oID, Model model) {
        model.addAttribute("user", new User());
        return "user_new";
    }

    /** Will save the new user if no problems were encountered. Will also redirect to user management. */
    @PostMapping("/organisation/{oID}/user_management/user_save")
    public String addNewUser(@PathVariable Long oID, @Valid User user, BindingResult bindingResult, Model model) {
        //More checks needed
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "user_new";
        }
        userRepository.save(user);
        return "redirect:/organisation/{oID}/user_management";
    }

    /** Deletes the specified user and redirects to user management. */
    @GetMapping("/organisation/{oID}/user_management/user/{uID}/delete")
    public String deleteUserById(@PathVariable Long oID, @PathVariable Long uID) {
        userRepository.deleteById(uID);
        return "redirect:/organisation/{oID}/user_management";
    }

    /** Shows the user edit page */
    @GetMapping("/organisation/{oID}/user_management/user/{uID}/user_edit")
    public String showUserById(@PathVariable Long oID, @PathVariable Long uID, Model model) {
        model.addAttribute("user", userRepository.findById(uID).get());
        return "user_edit";
    }

    /** Updates the user with the new data that was specified. Will redirect to user management if everything went well. */
    @PostMapping("/organisation/{oID}/user_management/user/{uID}/user_update")
    public String editUserById(@PathVariable Long oID, @PathVariable Long uID, @Valid User user, BindingResult bindingResult, Model model) {
        //More checks needed
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "user_edit";
        }
        user.setId(uID);
        userRepository.save(user);
        return "redirect:/organisation/{oID}/user_management";
    }
}