package com.lms2ue1.sbsweb.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.lang.annotation.Before;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;


class DiagramControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DiagramController controller;
	
//	WebClient webClient;
//	
//	@BeforeEach
//	void setup() {
//	    webClient = MockMvcWebClientBuilder
//	            .webAppContextSetup(context)
//	            .build();
//	}
//	@Test
//	public void contextLoads() throws Exception {
//		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
//		assertThat(controller).isNotNull();
//	}

}
