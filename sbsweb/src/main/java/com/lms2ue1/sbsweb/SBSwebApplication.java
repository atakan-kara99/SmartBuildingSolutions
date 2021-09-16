package com.lms2ue1.sbsweb;

import com.lms2ue1.sbsweb.backend.restapi.JSONDeserialiser;
import com.lms2ue1.sbsweb.service.DatabaseInitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SBSwebApplication implements CommandLineRunner {
    @Autowired
    DatabaseInitService databaseInitService;
    @Autowired
    JSONDeserialiser jsonDeserialiser;

    public static void main(String[] args) {
	SpringApplication.run(SBSwebApplication.class, args);
    }
    

    @Value("${testing.flag}")
    private boolean testingFlag;
    
    @Override
    public void run(String... arg0) throws Exception {

	databaseInitService.init();
	if (!testingFlag) {
	    jsonDeserialiser.deserialiseProjects();
	}

    }
}