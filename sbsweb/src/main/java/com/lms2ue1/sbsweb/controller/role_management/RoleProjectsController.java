package com.lms2ue1.sbsweb.controller.role_management;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

import com.lms2ue1.sbsweb.backend.model.BackendAccessProvider;
import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Project;
import com.lms2ue1.sbsweb.backend.model.Role;
import com.lms2ue1.sbsweb.backend.repository.UserRepository;
import com.lms2ue1.sbsweb.backend.security.AuthorisationCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controller managing web page for project related access management of a role
 * 
 * @author Luca Anthony Schwarz (sunfl0w)
 */
@Controller
public class RoleProjectsController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BackendAccessProvider backendAccessProvider;

    @Autowired
    private AuthorisationCheck auth;

    /**
     * Displays the project access web page.
     * 
     * @param  principal               Security principal to access current user's name
     * @param  oID                     ID of the relevant organisation
     * @param  rID                     ID of the relevant role
     * @param  model                   Spring model to provide instances to the web page
     * @throws AuthenticationException Thrown when current user ist not allowed to access specified organisation and role
     * @return                         URI of the HTML role edit project access page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_edit_access_projects")
    public String showProjectAccessPage(Principal principal, @PathVariable long oID, @PathVariable long rID, Model model) {
        Organisation organisation = null;
        Role role = null;
        List<Project> availableProjects = backendAccessProvider.getAllProjects(principal.getName());
        try {
            organisation = backendAccessProvider.getOrganisationById(principal.getName(), oID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        try {
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        // TODO Get user by name form BAP
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("adminPrivileges", auth.isSysAdmin(principal.getName()));
        model.addAttribute("organisation", organisation);
        model.addAttribute("role", role);
        model.addAttribute("availableProjects", availableProjects);
        model.addAttribute("accessibleProjects", role.getProjects());
        return "role/role_edit_access_projects";
    }

    /**
     * GET mapping wotking a bit like a post mapping but without passed object. Adds a project to a given role by the IDs specified in the path.
     * 
     * @param  principal               Security principal to access current user's name
     * @param  oID                     ID of the relevant organisation
     * @param  rID                     ID of the relevant role
     * @param  pID                     ID of the relevant project
     * @throws AuthenticationException Thrown when current user ist not allowed to access specified organisation and role
     * @return                         Redirect to the role edit project access page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_add_project/project/{pID}")
    public String addProjectToRole(Principal principal, @PathVariable long oID, @PathVariable long rID, @PathVariable long pID) {
        Role role = null;
        Project project = null;
        try {
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        try {
            project = backendAccessProvider.getProjectById(principal.getName(), pID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        List<Project> accessibleProjects = new ArrayList<Project>(role.getProjects());
        accessibleProjects.add(project);
        Role updatedRole =
        new Role(role.getName(), accessibleProjects, role.getContracts(), role.getBillingItems(), role.getOrganisation(), role.isManageUser());
        try {
            backendAccessProvider.updateRole(principal.getName(), role.getId(), updatedRole);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        return "redirect:/organisation/{oID}/role_management/role/{rID}/role_edit_access_projects";
    }

    /**
     * GET mapping wotking a bit like a post mapping but without passed object. Removes a project of a given role by the IDs specified in the path.
     * 
     * @param  principal               Security principal to access current user's name
     * @param  oID                     ID of the relevant organisation
     * @param  rID                     ID of the relevant role
     * @param  pID                     ID of the relevant project
     * @throws AuthenticationException Thrown when current user ist not allowed to access specified organisation and role
     * @return                         Redirect to the role edit project access page
     */
    @GetMapping("/organisation/{oID}/role_management/role/{rID}/role_remove_project/project/{pID}")
    public String removeProjectFromRole(Principal principal, @PathVariable long oID, @PathVariable long rID, @PathVariable long pID) {
        Role role = null;
        Project project = null;
        try {
            role = backendAccessProvider.getRoleById(principal.getName(), rID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        try {
            project = backendAccessProvider.getProjectById(principal.getName(), pID);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        List<Project> accessibleProjects = new ArrayList<Project>(role.getProjects());
        accessibleProjects.remove(project);
        Role updatedRole =
        new Role(role.getName(), accessibleProjects, role.getContracts(), role.getBillingItems(), role.getOrganisation(), role.isManageUser());
        try {
            backendAccessProvider.updateRole(principal.getName(), role.getId(), updatedRole);
        } catch (AuthenticationException authException) {
            authException.printStackTrace();
        }
        return "redirect:/organisation/{oID}/role_management/role/{rID}/role_edit_access_projects";
    }
}
