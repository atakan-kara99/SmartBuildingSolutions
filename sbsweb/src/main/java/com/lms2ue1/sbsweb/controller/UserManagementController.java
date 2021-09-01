package com.lms2ue1.sbsweb.controller;

import javax.validation.Valid;

import com.lms2ue1.sbsweb.model.User;
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

    // This will redirect home to user management for now
    @GetMapping("/")
    public String showUserManagementRedirect(Model model) {
        model.addAttribute("message", "Testnachricht");
        return "redirect:user_management";
    }

    @GetMapping("/user_management")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user_management";
    }

    @GetMapping("/user_management/user_new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user_new";
    }

    @PostMapping("/user_management/user_save")
    public String addNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        //More checks needed
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "user_new";
        }
        userRepository.save(user);
        return "redirect:/user_management";
    }

    @GetMapping("/user_management/user/{uid}/delete")
    public String deleteUserById(@PathVariable Long uid) {
        userRepository.deleteById(uid);
        return "redirect:/user_management";
    }

    @PostMapping("/user_management/user/{uid}/edit")
    public String editUserById(@PathVariable Long uid, Model model) {
        model.addAttribute("user", userRepository.findById(uid));
        return "user_edit";
    }
}