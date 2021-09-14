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
    StatusRepository addRepo;
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
	    Status deny = new Status("DENY", ok);
	    Status open = new Status("OPEN", deny);
	    Status noStatus = new Status("NO_STATUS", open);
	    
	    statRepo.saveAll(List.of(ok, deny, open, noStatus));
	}
    }

    /*
     * public void init() { if (userRepo.count() == 0) { BCryptPasswordEncoder
     * passwordEncoder = new BCryptPasswordEncoder();
     * 
     * // -----------------------------// // ------------- Status ----------// //
     * -----------------------------// Status s1 = new Status("OK",
     * "everything finished", null); Status s2 = new
     * Status("OPEN","still in progress", s1); Status s3 = new Status("NO Status",
     * "waiting for starting", s2);
     * 
     * statRepo.save(s1); statRepo.save(s2); statRepo.save(s3);
     * 
     * // -----------------------------// // ------------- Orga ----------// //
     * -----------------------------// Organisation org0 = new
     * Organisation("SBS AG"); Organisation org1 = new
     * Organisation("Tiefbau Co Kg."); Organisation org3 = new
     * Organisation("Uni-Kiel GmbH"); Organisation org4 = new
     * Organisation("Apple INC."); Organisation org5 = new
     * Organisation("Microsoft INC."); Organisation org6 = new
     * Organisation("Pineapple GmbH");
     * 
     * orgaRepo.save(org0); orgaRepo.save(org1); orgaRepo.save(org3);
     * orgaRepo.save(org4); orgaRepo.save(org5); orgaRepo.save(org6);
     * 
     * List<Organisation> organisations = List.of(org0, org1); List<Organisation>
     * organisations1 = List.of(org3, org4); List<Organisation> organisations2 =
     * List.of(org5, org6);
     * 
     * // -----------------------------// // ------------- Address -------// //
     * -----------------------------// Address add0 = new Address("Kein Weg", 1,
     * 24111, "Bei Berlin City", "weißt-du-doch-nicht-land"); Address add1 = new
     * Address("Olshausenstr.", 111, 24107, "Kiel", "Deutschland"); Address add2 =
     * new Address("Nebelungen-Weg", 007, 98745, "Nimmerland", "Peter-Pan-Land");
     * Address add3 = new Address("Nice Ave.", 3457, 15341, "New York",
     * "Süd-Afrika"); Address add4 = new Address("Beim-dicken-Michi", 1, 98745,
     * "Moskau", "Russland");
     * 
     * addRepo.save(add0); addRepo.save(add1); addRepo.save(add2);
     * addRepo.save(add3); addRepo.save(add4);
     * 
     * // -----------------------------// // ------------- Project -------// //
     * -----------------------------// Project pro0 = new Project("pro0", null,
     * null, null, 0, "root", null, null, null, add0, org0); Project pro1 = new
     * Project("Burj Khalifa2", "steht direkt daneben", "2010-02-07", "2021-06-01",
     * 234578900, "Die den anderen Turm auch gemacht haben", null, null, null, add3,
     * org3); Project pro2 = new Project("Berliner Flughafen xD",
     * "Morgen ist es soweit", "2010-12-28", "2015-01-01", 1300500000,
     * "Nicht die vom Burj Khalifa", null, null, null, add2, org5);
     * 
     * proRepo.save(pro0); proRepo.save(pro1); proRepo.save(pro2);
     * 
     * Project pro3 = new Project("Schule sanieren", null, null, null, 0, null,
     * null, null, null, null,
     * 
     * org0); Project pro4 = new Project("Hausbau",
     * "Haus an der Lindenallee 37 wird gebaut", "23.08.2021", "19.09.2021", 0,
     * "AG Haustechnik", null, null, null, add1, org0);
     * 
     * Project pro5 = new Project("Feierabend XTREME", null, null, null, 0, null,
     * null, null, null, null, org0); proRepo.saveAll(List.of(pro3, pro4, pro5));
     * 
     * // ------------------------------// // ------------- Contract -------// //
     * ------------------------------// Contract con0 = new Contract("con0", null,
     * null, null, organisations, pro0); Contract con1 = new
     * Contract("Vertrag für das neue Fenster", "auf nachfrage", "Jörg", "Peter",
     * organisations1, pro1); Contract con2 = new Contract("Haus-Restaurierung",
     * "eilauftrag", "Microsoft INC", "Apple INC", organisations2, pro2);
     * 
     * conRepo.save(con0); conRepo.save(con1); conRepo.save(con2);
     * 
     * Contract con3 = new Contract("Wohnzimmer bauen",
     * "Sachen m�ssen erledigt werden", null, null, null, pro4); Contract con4 = new
     * Contract("Küche installieren", null, null, null, null, pro4); Contract con5 =
     * new Contract("Baby beruhigen", null, null, null, null, pro4); Contract con6 =
     * new Contract("Kosten klein halten", null, null, null, null, pro4);
     * conRepo.saveAll(List.of(con3, con4, con5, con6));
     * 
     * // ------------------------------// // ------------- BillingUnit ----// //
     * ------------------------------// BillingUnit billUnit0 = new
     * BillingUnit(null, null, null, null, null, 0, 0, con0, s1);
     * 
     * billUnitRepo.save(billUnit0);
     * 
     * BillingUnit billUnit1 = new BillingUnit(null, null, null, null, null, 0, 0,
     * con3, s3); billUnitRepo.save(billUnit1);
     * 
     * // ------------------------------// // ------------- BillingItem ----// //
     * ------------------------------// BillingItem billItem0 = new
     * BillingItem("bill0", 0, null, null, 0, null, 0, null, null, billUnit0, null);
     * BillingItem nested = new BillingItem("Schrauben kaufen", 0, "Sind leer.",
     * null, 0, null, 0, null, null, billUnit1, List.of());
     * 
     * billItemRepo.save(billItem0); billItemRepo.save(nested);
     * 
     * BillingItem billItem1 = new BillingItem("Heizung montieren", 0,
     * "Heizkörper B7-2 fensternah einbauen.", s1, 0, null, 0, null, null,
     * billUnit1,null);
     * 
     * BillingItem billItem2 = new BillingItem("Fenster einbauen", 0, null, s3, 0,
     * null, 0, null, null, billUnit1, null);
     * billItemRepo.saveAll(List.of(billItem1, billItem2));
     * 
     * // -----------------------------// // ------------- Role ----------// //
     * -----------------------------// Role role0 = new Role("SysAdmin",
     * List.of(pro3, pro4, pro5), List.of(con3, con4, con5, con6),
     * List.of(billItem1, billItem2), org0, true); Role role1 = new Role("OrgAdmin",
     * null, null, null, org1, true); Role r1 = new Role("Bauherr", null, null,
     * null, org5, false); Role r2 = new Role("Bauherr", null, null, null, org3,
     * false); Role r3 = new Role("Handwerker", null, null, null, org5, false);
     * 
     * roleRepo.save(role0); roleRepo.save(role1); roleRepo.save(r1);
     * roleRepo.save(r2); roleRepo.save(r3);
     * 
     * // List<Role> roleList = List.of(role0, role1, r1, r2, r3);
     * 
     * // -----------------------------// // ------------- User ----------// //
     * -----------------------------// User user0 = new User("Peter", "Müller",
     * role0, "root", passwordEncoder.encode("admin")); User u1 = new
     * User("Anis Yusuf Muhammad", "Ferchichi", r3, "Bushido",
     * passwordEncoder.encode("kayone")); User u2 = new User("Hans", "im Glück", r1,
     * "hansi-happiness", passwordEncoder.encode("stycks")); User u3 = new
     * User("Spongebog", "Schwammkopf", r3, "spongi",
     * passwordEncoder.encode("patrick")); User u4 = new User("John", "Doe", r2,
     * "durak", passwordEncoder.encode("jonny"));
     * 
     * userRepo.save(user0); userRepo.save(u1); userRepo.save(u2);
     * userRepo.save(u3); userRepo.save(u4);
     * 
     * } }
     */

}
