package com.lms2ue1.sbsweb.service;

import com.lms2ue1.sbsweb.model.User;
import com.lms2ue1.sbsweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitService {
    @Autowired
    UserRepository userRepo;
    
    public void init() {
        if(userRepo.count() == 0) {
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        	
            User user0 = new User(0l, "Peter", "Müller", "SBS-Management", "SysAdmin", "sysadmin", passwordEncoder.encode("admin"));
            //User user1 = new User(1l, "Anna", "Krause", "Hochbau-Krause", "OrgAdmin", "AKrause", "krause");
            //User user2 = new User(1l, "Hans-Dieter", "Schulze", "Hochbau-Krause", "Bauleiter", "HDS", "schulze");

            userRepo.save(user0);
            //userRepository.save(user1);
            //userRepository.save(user2);
        }
    }
}
