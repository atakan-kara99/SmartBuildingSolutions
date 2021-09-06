package com.lms2ue1.sbsweb.service;

import com.lms2ue1.sbsweb.model.Organisation;
import com.lms2ue1.sbsweb.model.User;
import com.lms2ue1.sbsweb.repository.OrganisationRepository;
import com.lms2ue1.sbsweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganisationRepository organisationRepository;
    
    public void init() {
        if(userRepository.count() == 0) {
            User user0 = new User(0l, "Peter", "Müller", "SBS-Management", "SysAdmin", "SysAdmin");
            User user1 = new User(0l, "Anna", "Krause", "Hochbau-Krause", "OrgAdmin", "AKrause");
            User user2 = new User(0l, "Hans-Dieter", "Schulze", "Hochbau-Krause", "Bauleiter", "HDS");

            userRepository.save(user0);
            userRepository.save(user1);
            userRepository.save(user2);
        }

        if(organisationRepository.count() == 0) {
            Organisation organisation0 = new Organisation(0, "Hochbau-Krause");

            organisationRepository.save(organisation0);
        }
    }
}
