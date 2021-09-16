package com.lms2ue1.sbsweb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.repository.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProjectRepository projects;
    @Autowired
    private ContractRepository contracts;
    @Autowired
    private BillingUnitRepository billingUnits;
    @Autowired
    private BillingItemRepository billingItems;
    @Autowired
    private OrganisationRepository organisations;
    @Autowired
    private UserRepository users;
    @Autowired
    private RoleRepository roles;

    // Root

    @Test
    @WithMockUser(username = "root", roles = { "SysAdmin" })
    public void testProjectOverviewRoot() throws Exception {
	mvc.perform(get("/project_overview")).andExpect(status().isOk())
		.andExpect(view().name("project/project_overview")).andExpect(model().attributeExists("projects"))
		.andExpect(model().attribute("projects", projects.findAll()));
    }

    @Test
    @WithMockUser(username = "root", roles = { "SysAdmin" })
    public void testProjectDetailsRoot() throws Exception {
	for (Project pro : projects.findAll()) {
	    // findAll() should never return null
	    mvc.perform(get("/project/" + pro.getId() + "/show")).andExpect(status().isOk())
		    .andExpect(view().name("project/project_details")).andExpect(model().attributeExists("project"))
		    .andExpect(model().attribute("project", pro)).andExpect(model().attributeExists("contracts"));
	}
    }

    @Test
    @WithMockUser(username = "root", roles = { "SysAdmin" })
    public void testOrganisationOverviewRoot() throws Exception {
	mvc.perform(get("/organisations")).andExpect(status().isOk())
		.andExpect(view().name("organisation/organisation_management"))
		.andExpect(model().attributeExists("organisations"))
		.andExpect(model().attribute("organisations", organisations.findAll()));
    }

    @Test
    @WithMockUser(username = "root", roles = { "SysAdmin" })
    public void testOrganisationDetailsRoot() throws Exception {
	// TODO
	for (Organisation org : organisations.findAll()) {
	    mvc.perform(get("/organisation/" + org.getId() + "TODO")).andExpect(status().isOk());
//		    .andExpect(view().name("organisation/organisation_management"))
//		    .andExpect(model().attributeExists("organisations"))
//		    .andExpect(model().attribute("organisations", organisations.findAll()));
	}
    }

    // App API

    @Test
    @WithMockUser(username = "root", roles = { "SysAdmin" })
    public void testJWTToken() throws Exception {
	mvc.perform(post("/api/auth")); // TODO
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
}
