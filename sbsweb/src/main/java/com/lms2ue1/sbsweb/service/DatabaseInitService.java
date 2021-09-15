package com.lms2ue1.sbsweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.repository.*;

@Service
public class DatabaseInitService {

    @Autowired
    UserRepository userRepo;
    @Autowired
    OrganisationRepository orgaRepo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    ProjectRepository proRepo;
    @Autowired
    ContractRepository conRepo;
    @Autowired
    BillingUnitRepository billUnitRepo;
    @Autowired
    BillingItemRepository billItemRepo;
    @Autowired
    StatusRepository statRepo;

    public void init() {
	if (userRepo.count() == 0) {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	    // The SysAdmin is always here!
	    Role sysAdminRole = new Role("SysAdmin", null, null, null, null, true);
	    roleRepo.save(sysAdminRole);
	    User sysAdmin = new User("Peter", "Müller", sysAdminRole, "root", passwordEncoder.encode("admin"));
	    userRepo.save(sysAdmin);

	    // ----- Initial Stati -------//
	    Status ok = new Status("OK", null);
	    Status deny = new Status("DENY", List.of(ok));
	    Status open = new Status("OPEN", List.of(deny));
	    Status planned = new Status("PLANNED", List.of(open));
	    Status noStatus = new Status("NO_STATUS", List.of(planned));

	    statRepo.saveAll(List.of(ok, deny, open, noStatus, planned));
	}
    }
}