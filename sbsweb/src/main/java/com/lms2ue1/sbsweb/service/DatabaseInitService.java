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
        if(userRepository.count() == 0 && organisationRepository.count() == 0) {

            Organisation org1 = new Organisation(0l, "SBS-Management");
            Organisation org2 = new Organisation(1l, "Hochbau-Krause");


            User user0 = new User("Peter", "Müller", org1, "SysAdmin", "SysAdmin");
            User user1 = new User("Anna", "Krause", org2, "OrgAdmin", "AKrause");
            User user2 = new User("Hans-Dieter", "Schulze", org2, "Bauleiter", "HDS");

            org1.getUsers().add(user0);
            org2.getUsers().add(user1);
            org2.getUsers().add(user2);
            userRepository.save(user0);
            userRepository.save(user1);
            userRepository.save(user2);

            organisationRepository.save(org1);
            organisationRepository.save(org2);
        }
    }
}
