package com.lms2ue1.sbsweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.repository.*;


@Service
public class DatabaseInitService {
	
	@Autowired
	AddressRepository addRepo;
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
    
    
    public void init() {
        if(userRepo.count() == 0) {
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        	
        	//-----------------------------//
       	   //------------- Orga ----------//
       	  //-----------------------------//
        	Organisation org0 = new Organisation("SBS");
        	
        	orgaRepo.save(org0);
        	
        	//-----------------------------//
        	//------------- Role ----------//
        	//-----------------------------//
        	Role role0 = new Role(0, "SysAdmin", null, org0, null, null);
        	
        	//-----------------------------//
        	//------------- User ----------//
        	//-----------------------------//
        	List<Organisation> organisations = new ArrayList<Organisation>();
        	organisations.add(org0);
        	User user0 = new User("Peter", "Müller", organisations, role0, "root", passwordEncoder.encode("admin"));
        	
        	userRepo.save(user0);
        	
        	 //-----------------------------//
            //------------- Address -------//
           //-----------------------------//
        	Address add0 = new Address(null, 0, 0, null, null);
        	
        	//-----------------------------//
           //------------- Project -------//
          //-----------------------------//
        	Project pro0 = new Project("pro0", null, null, null, Status.NO_Status, 0, "root", null, null, null, add0, org0);
        	
        	roleRepo.save(role0);
        	
        	//------------------------------//
           //------------- Contract -------//
          //------------------------------//
        	Contract con0 = new Contract("con0", null, Status.NO_Status, null, null, organisations, pro0);
        	
        	conRepo.save(con0);
        	
        	 //------------------------------//
            //------------- BillingUnit ----//
           //------------------------------//
        	BillingUnit billUnit0 = new BillingUnit(null, null, null, null, null, 0, 0, con0);
        	
        	//------------------------------//
           //------------- BillingItem ----//
          //------------------------------//
        	BillingItem billItem0 = new BillingItem(0, null, Status.NO_Status, 0, null, 0, null, null, billUnit0, null);
        	
        	billItemRepo.save(billItem0);
        }
    }
}
