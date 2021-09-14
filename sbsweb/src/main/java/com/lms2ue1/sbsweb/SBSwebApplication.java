package com.lms2ue1.sbsweb;

import com.lms2ue1.sbsweb.service.DBSynchronisationService;
import com.lms2ue1.sbsweb.service.DatabaseInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SBSwebApplication implements CommandLineRunner {
    @Autowired
    DatabaseInitService databaseInitService;
    @Autowired
    DBSynchronisationService dbSyncService;

    public static void main(String[] args) {
	SpringApplication.run(SBSwebApplication.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
	//databaseInitService.init();
	dbSyncService.init();
    }
}