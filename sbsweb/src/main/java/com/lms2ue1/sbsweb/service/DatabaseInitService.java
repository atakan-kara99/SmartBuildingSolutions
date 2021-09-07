package com.lms2ue1.sbsweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.repository.UserRepository;


@Service
public class DatabaseInitService {
	
    @Autowired
    UserRepository userRepo;
    
    public void init() {
        if(userRepo.count() == 0) {
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        	
            //User user0 = new User(0l, "Peter", "Müller", "SBS-Management", "SysAdmin", "sysadmin", passwordEncoder.encode("admin"));
        	List<Organisation> organisations = new ArrayList<Organisation>();
        	organisations.add(new Organisation());
        	User user0 = new User("Peter", "Müller", "SysAdmin", organisations, "root", passwordEncoder.encode("admin"));

            userRepo.save(user0);
        }
    }
}
