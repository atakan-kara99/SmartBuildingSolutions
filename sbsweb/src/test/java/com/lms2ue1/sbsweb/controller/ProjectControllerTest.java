package com.lms2ue1.sbsweb.controller;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.ServletContext;
import org.mockito.junit.MockitoJUnitRunner;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.lms2ue1.sbsweb.backend.model.Address;
import com.lms2ue1.sbsweb.backend.model.BillingUnit;
import com.lms2ue1.sbsweb.backend.model.Contract;
import com.lms2ue1.sbsweb.backend.model.Organisation;
import com.lms2ue1.sbsweb.backend.model.Project;
import com.lms2ue1.sbsweb.backend.repository.ProjectRepository;


//@ExtendWith(SpringExtension.class)
//@ContextConfiguration
//@WebAppConfiguration
//@AutoConfigureMockMvc
public class ProjectControllerTest {
/*
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private WebApplicationContext context;
    
    @InjectMocks
    private ProjectController proCon;
 
    @Autowired
	private MockMvc mockMvc;
    
    @MockBean
    ProjectRepository proRepo;
    
    // Invars
    private Organisation org0;
    private Address address0;
    private Address address1;
    private Project pro0;
    private Project pro1;


	
//	@BeforeEach
//	void setup() {
//	    webClient = MockMvcWebClientBuilder
//	            .webAppContextSetup(context)
//	            .build();
//	}
	
	@BeforeEach
	public void init() {
		
		org0 = new Organisation("SBS");
		address0 = new Address("Main Street", 1337, 14, "NY City", "Deutschland");
		address1 = new Address("2nd Street", 42, 15, "GBR City", "France");
		pro0 = new Project("Burj Khalifa2", "steht direkt daneben", "2010-02-07", "2021-06-01", null,
			234578900, "Die den anderen Turm auch gemacht haben", null, null, null, address0, org0);
		pro1 = new Project("Berliner Flughafen xD", "Morgen ist es soweit", "2010-12-28", "2015-01-01", null,
			1300500000, "Nicht die vom Burj Khalifa", null, null, null, address1, org0);

		
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
//		when(userMock.findByUsername("root")).thenReturn(user0);
	}
	
	@Test
	public void givenWac_whenServletContext_thenItProvidesProjectController() {
	    ServletContext servletContext = context.getServletContext();
	    
	    assertNotNull(servletContext);
	    assertTrue(servletContext instanceof MockServletContext);
	    assertNotNull(context.getBean("proCon"));
	}
	
	@Test
	public void getUrl_whenMockMvc_returnCorrectView() throws Exception{
		
		when(proRepo.findAll()).thenReturn(List.of(pro0, pro1));
		
		this.mockMvc.perform(get("/project_overview"))
			.andDo(print())
            .andExpect(model().size(2));
//			.andExpect(view().name("project/project_overview"));
	}
	
//	@Test
//	public void test() throws Exception {
		
//		when(proMock.findAll()).thenReturn(Arrays.asList(project0,project1));
//		
//		mockMvc.perform(get("/project_overview"));
//				.andExpect(status().isOk());
//				.andExpect(view().name("project/project_overview"));
//				.andExpect(model().attribute("projects", hasSize(2)));
//				.andExpect(model().attribute("projects", hasItem(
//						allOf(
//								hasProperty("name", is("pro0"))
//						)
//				)))
//				.andExpect(model().attribute("projects", hasItem(
//						allOf(
//								hasProperty("name", is("pro1"))
//						)
//				)));
		
//        verify(proMock, times(1)).findAll();
//        verifyNoMoreInteractions(proMock);
//	}
*/
}
