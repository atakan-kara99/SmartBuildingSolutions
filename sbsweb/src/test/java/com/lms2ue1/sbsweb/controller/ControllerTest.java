package com.lms2ue1.sbsweb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.lms2ue1.sbsweb.backend.model.Project;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    // Root

    @Test
    @WithMockUser(username = "root", roles = { "SysAdmin" })
    public void testProjectOverviewRoot() throws Exception {
	mvc.perform(get("/project_overview")).andExpect(status().isOk())
		.andExpect(view().name("project/project_overview"));
    }

    @Test
    @WithMockUser(username = "root", roles = { "SysAdmin" })
    public void testOrganisationOverviewRoot() throws Exception {
	mvc.perform(get("/organisations")).andExpect(status().isOk())
		.andExpect(view().name("organisation/organisation_management"));
    }

    // Random person, no roles

    @Test
    @WithMockUser(username = "hans")
    public void testProjectOverviewRandom() throws Exception {
	mvc.perform(get("/project_overview")).andExpect(view().name("error"));
    }

    @Test
    @WithMockUser(username = "hans")
    public void testOrganisationOverviewRandom() throws Exception {
	mvc.perform(get("/organisations")).andExpect(view().name("error"));
    }

    // Anonymous

    @Test
    @WithAnonymousUser
    public void testProjectOverviewAnonymous() throws Exception {
	mvc.perform(get("/project_overview")).andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithAnonymousUser
    public void testOrganisationOverviewAnonymous() throws Exception {
	mvc.perform(get("/organisations")).andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrlPattern("**/login"));
    }

    // TODO
    @Test
//    @WithMockUser(username = "root", roles = { "SysAdmin" })
    @WithMockUser(username = "hans", roles = { "Unwichtig" })
    public void testIfHomePageCanBeShown() throws Exception {
	List<Project> projects = List.of(
		new Project("Name", "Beschreibung", "2020", "2021", null, 1.29, "Hans", null, null, null, null, null));
	ResultMatcher model = MockMvcResultMatchers.model().attribute("projects", projects);
	mvc.perform(get("/project_overview")).andExpect(status().isOk()).andExpect(model);
    }
}
