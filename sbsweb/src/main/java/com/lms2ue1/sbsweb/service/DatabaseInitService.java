package com.lms2ue1.sbsweb.service;

import com.lms2ue1.sbsweb.model.User;
import com.lms2ue1.sbsweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitService {
    @Autowired
    UserRepository userRepository;
    
    public void init() {
        if(userRepository.count() == 0) {
            
        }
    }
}
