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
//	if (userRepo.count() == 0) {
//	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//	    // -----------------------------//
//	    // ------------- Orga ----------//
//	    // -----------------------------//
//	    Organisation org0 = new Organisation("SBS");
//	    Organisation org1 = new Organisation("Tiefbau");
//
//	    orgaRepo.save(org0);
//	    orgaRepo.save(org1);
//
//	    List<Organisation> organisations = List.of(org0, org1);
//
//	    // -----------------------------//
//	    // ------------- Role ----------//
//	    // -----------------------------//
//	    Role role0 = new Role("SysAdmin", null, null, null, org0);
//	    Role role1 = new Role("OrgAdmin", null, null, null, org1);
//
//	    roleRepo.save(role0);
//	    roleRepo.save(role1);
//
//	    // List<Role> roleList = List.of(role0, role1);
//
//	    // -----------------------------//
//	    // ------------- User ----------//
//	    // -----------------------------//
	    User user0 = new User("Peter", "Müller", role0, "root", passwordEncoder.encode("admin"));

	    userRepo.save(user0);
//
//	    // -----------------------------//
//	    // ------------- Address -------//
//	    // -----------------------------//
//	    Address add0 = new Address(null, 0, 0, null, null);
//
//	    addRepo.save(add0);
//
//	    // -----------------------------//
//	    // ------------- Project -------//
//	    // -----------------------------//
//	    Project pro0 = new Project("pro0", null, null, null, Status.NO_STATUS, 0, "root", null, null, null, add0,
//		    org0);
//	    Project pro1 = new Project("Schule sanieren", null, null, null, null, 0, null, null, null, null, null,
//		    null);
//	    Project pro2 = new Project("Hausbau", "Haus an der Lindenallee 37 wird gebaut", null, null,
//		    Status.NO_STATUS, 0, null, null, null, null, null, null);
//	    Project pro3 = new Project("Feierabend XTREME", null, null, null, null, 0, null, null, null, null, null,
//		    null);
//
//	    proRepo.save(pro0);
//	    proRepo.saveAll(List.of(pro1, pro2, pro3));
//
//	    // ------------------------------//
//	    // ------------- Contract -------//
//	    // ------------------------------//
//	    Contract con0 = new Contract("con0", null, Status.NO_STATUS, null, null, organisations, pro0);
//	    Contract con1 = new Contract("Wohnzimmer bauen", "Sachen m�ssen erledigt werden", null, null, null, null,
//		    pro2);
//	    Contract con2 = new Contract("K�che installieren", null, null, null, null, null, pro2);
//	    Contract con3 = new Contract("Baby beruhigen", null, null, null, null, null, pro2);
//	    Contract con4 = new Contract("Kosten klein halten", null, null, null, null, null, pro2);
//
//	    conRepo.save(con0);
//	    conRepo.saveAll(List.of(con1, con2, con3, con4));
//
//	    // ------------------------------//
//	    // ------------- BillingUnit ----//
//	    // ------------------------------//
//	    BillingUnit billUnit0 = new BillingUnit(null, null, null, null, null, 0, 0, con0);
//	    BillingUnit billUnit1 = new BillingUnit(null, null, null, null, null, 0, 0, con1);
//
//	    billUnitRepo.save(billUnit0);
//	    billUnitRepo.save(billUnit1);
//
//	    // ------------------------------//
//	    // ------------- BillingItem ----//
//	    // ------------------------------//
//	    BillingItem billItem0 = new BillingItem("bill0", 0, null, Status.NO_STATUS, 0, null, 0, null, null,
//		    billUnit0, null);
//	    BillingItem billItem1 = new BillingItem("Heizung montieren", 0, "Heizk�rper B7-2 fensternah einbauen.",
//		    Status.OPEN, 0, null, 0, null, null, billUnit1, null);
//	    BillingItem billItem2 = new BillingItem("Fenster einbauen", 0, null, null, 0, null, 0, null, null,
//		    billUnit1, null);
//
//	    billItemRepo.save(billItem0);
//	    billItemRepo.saveAll(List.of(billItem1, billItem2));
//	}
    }
}
