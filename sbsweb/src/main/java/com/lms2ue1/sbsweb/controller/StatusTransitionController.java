package com.lms2ue1.sbsweb.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lms2ue1.sbsweb.backend.model.BackendAccessProvider;
import com.lms2ue1.sbsweb.backend.model.Status;
import com.lms2ue1.sbsweb.backend.security.AuthorisationCheck;

@Controller
public class StatusTransitionController {

    @Autowired
    private BackendAccessProvider BAP;
    @Autowired
    private AuthorisationCheck auth;

    /** Shows an overview of all stati. */
    @GetMapping("/status_overview")
    public String showStati(Principal principal, Model model) {
	String username = principal.getName();
	if (!auth.isSysAdmin(username)) {
	    return "error";
	}
	model.addAttribute("allStati", BAP.getAllStati());
	return "status/status_overview";
    }

    /** Shows a status details. */
    @GetMapping("/status/{sID}/show")
    public String showStatusDetails(@PathVariable long sID, Principal principal, Model model) {
	String username = principal.getName();
	if (!auth.isSysAdmin(username)) {
	    return "error";
	}
	Status status = BAP.getStatusById(sID);
	model.addAttribute("sID", sID);
	model.addAttribute("status", status);
	model.addAttribute("nextStati", status.getNextStati());
	model.addAttribute("allStati", BAP.getAllStati());
	model.addAttribute("standardStatus", BAP.isStandardStatusById(sID));
	return "status/status_details";
    }

    /** Shows the form to create a new status. */
    @GetMapping("/status_new")
    public String showNewStatusForm(Principal principal, Model model) {
	String username = principal.getName();
	if (!auth.isSysAdmin(username)) {
	    return "error";
	}
	model.addAttribute("allStati", BAP.getAllStati());
	model.addAttribute("status", new Status());
	return "status/status_new";
    }

    /**
     * Tries to save the new status.
     * 
     * @throws AuthenticationException
     */
    @PostMapping("/status_save")
    public String addNewStatus(@Valid Status status, BindingResult bindingResult, Principal principal, Model model)
	    throws AuthenticationException {
	String username = principal.getName();
	if (!auth.isSysAdmin(username)) {
	    return "error";
	} else if (bindingResult.hasErrors()) {
	    model.addAttribute("status", status);
	    return "status/status_new";
	}
	boolean nameTaken = false;
	try {
	    BAP.getStatusByName(status.getName());
	    nameTaken = true;
	} catch (IllegalArgumentException e) {
	    // Throws = doesn't exist yet, everything's fine
	}
	if (nameTaken) {
	    throw new IllegalArgumentException("Status with name " + status.getName() + " already exists!");
	}

	BAP.addStatus(username, status);
	return "redirect:/status_overview";
    }

    /** Shows the form to edit a status. */
    @GetMapping("/status/{sID}/status_edit")
    public String showEditStatusForm(@PathVariable long sID, Principal principal, Model model) {
	String username = principal.getName();
	if (!auth.isSysAdmin(username)) {
	    return "error";
	}
	model.addAttribute("sID", sID);
//	model.addAttribute("status", BAP.getStatusById(sID));
	Status status = BAP.getStatusById(sID);
	model.addAttribute("allStati", BAP.getAllStati());
	model.addAttribute("currentTransitions", status.getNextStati());
	model.addAttribute("standardStatus", BAP.isStandardStatusById(sID));
	StatusCreationDto form = new StatusCreationDto();
	form.setName(status.getName());
	form.setNextStati(status.getNextStati());
	model.addAttribute("form", form);
	
//	model.addAttribute("updatedStatus", new Status(null, null, new ArrayList<>(10)));
	return "status/status_edit";
    }

    /**
     * Tries to delete the chosen status.
     * 
     * @throws AuthenticationException
     */
    @GetMapping("/status/{sID}/status_delete")
    public String deleteStatus(@PathVariable long sID, Principal principal, Model model)
	    throws AuthenticationException {
	String username = principal.getName();
	if (!auth.isSysAdmin(username)) {
	    return "error";
	} else if (BAP.isStandardStatusById(sID)) {
	    throw new IllegalArgumentException("Cannot delete NO_STATUS, OPEN, OK or DENY!");
	}
	BAP.removeStatus(username, sID);
	return "redirect:/status_overview";
    }

    /**
     * Tries to update the status.
     * 
     * @throws AuthenticationException
     */
    @PostMapping("/status/{sID}/status_update")
    public String updateStatus(@PathVariable long sID, @ModelAttribute StatusCreationDto form, BindingResult bindingResult,
	    Principal principal, Model model) throws AuthenticationException {
	String username = principal.getName();
	if (!auth.isSysAdmin(username)) {
	    return "error";
	} else if (bindingResult.hasErrors()) {
	    return "status/status_edit";
	}
	Status status = new Status(form.getName(), form.getDescription(), form.getNextStati());
	BAP.updateStatus(username, sID, status);
	return "redirect:/status/{sID}/show";
    }
}
