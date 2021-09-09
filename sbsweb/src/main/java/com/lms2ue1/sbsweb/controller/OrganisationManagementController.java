package com.lms2ue1.sbsweb.controller;

import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrganisationManagementController {

    @Autowired
    OrganisationRepository organisationRepository;

    /**
     * Shows an overview of all organisations.
     */
    @GetMapping("/organisations")
    public String showOrganisationList(Model model) {
        model.addAttribute("organisations", organisationRepository.findAll());
        return "organisation/organisation_management";
    }

    @GetMapping("/organisation/{oId}/users")
    public String showUsersOfOrganisation(@PathVariable Long oId, Model model) {
        model.addAttribute("organisation", organisationRepository.findById(oId).get());
        model.addAttribute("users", organisationRepository.findById(oId).get().getRoles().stream().map(role -> role.getUsers()).flatMap(List::stream).collect(Collectors.toList()));
        return "organisation/organisation_users";
    }

    @GetMapping("/organisations/new")
    public String showNewOrganisationPage(Model model) {
        model.addAttribute("organisation", new Organisation());
        return "organisation/organisation_new";
    }

    @PostMapping("/organisations/save")
    public String saveOrganisation(@Valid Organisation organisation, BindingResult bindingResult, Model model) {
        // Check if all constraints are met
        if (bindingResult.hasErrors()) {
            model.addAttribute("organisation", organisation);
            return "organisation/organisation_new";
        }
        organisationRepository.save(organisation);
        return "redirect:/organisations";
    }

    @GetMapping("/organisation/{oId}/delete")
    public String deleteOrganisationByOId(@PathVariable Long oId) {
        organisationRepository.deleteById(oId);
        return "redirect:/organisations";
    }

    @GetMapping("/organisation/{oId}/edit")
    public String showOrganisationByOId(@PathVariable Long oId, Model model) {
        model.addAttribute("organisation", organisationRepository.findById(oId).get());
        return "organisation/organisation_edit";
    }

    @PostMapping("/organisation/{oId}/edit")
    public String updateOrganisationById(@PathVariable Long oId,
                                         @Valid Organisation organisation, BindingResult bindingResult, Model model) {
        // Check if all constraints are met
        if (bindingResult.hasErrors()) {
            model.addAttribute("organisation", organisation);
            return "organisation/organisation_edit";
        }
        organisation.setId(oId);
        organisationRepository.save(organisation);
        return "redirect:/organisations";

    }
}