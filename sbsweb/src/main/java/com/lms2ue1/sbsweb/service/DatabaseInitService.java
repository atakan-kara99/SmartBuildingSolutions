package com.lms2ue1.sbsweb.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms2ue1.sbsweb.backend.repository.UserRepository;

@Service
public class DatabaseInitService {
    @Autowired
    UserRepository userRepository;
    
    public void init() {
    }
}
