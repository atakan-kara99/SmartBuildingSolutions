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
		if (userRepo.count() == 0) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			// -----------------------------//
			// ------------- Orga ----------//
			// -----------------------------//
			Organisation org0 = new Organisation("SBS AG");
			Organisation org1 = new Organisation("Tiefbau Co Kg.");
			Organisation org3 = new Organisation("Uni-Kiel GmbH");
			Organisation org4 = new Organisation("Apple INC.");
			Organisation org5 = new Organisation("Microsoft INC.");
			Organisation org6 = new Organisation("Pineapple GmbH");

			orgaRepo.save(org0);
			orgaRepo.save(org1);
			orgaRepo.save(org3);
			orgaRepo.save(org4);
			orgaRepo.save(org5);
			orgaRepo.save(org6);

			List<Organisation> organisations = List.of(org0, org1);
			List<Organisation> organisations1 = List.of(org3, org4);
			List<Organisation> organisations2 = List.of(org5, org6);

			// -----------------------------//
			// ------------- Role ----------//
			// -----------------------------//
			Role role0 = new Role("SysAdmin", null, null, null, org0);
			Role role1 = new Role("OrgAdmin", null, null, null, org1);
			Role r1 = new Role("Bauherr", null, null, null, org5);
			Role r2 = new Role("Bauherr", null, null, null, org3);
			Role r3 = new Role("Handwerker", null, null, null, org5);

			roleRepo.save(role0);
			roleRepo.save(role1);
			roleRepo.save(r1);
			roleRepo.save(r2);
			roleRepo.save(r3);

			// List<Role> roleList = List.of(role0, role1, r1, r2, r3);

			// -----------------------------//
			// ------------- User ----------//
			// -----------------------------//
			User user0 = new User("Peter", "Müller", role0, "root", passwordEncoder.encode("admin"));
			User u1 = new User("Anis Yusuf Muhammad", "Ferchichi", r3, "Bushido", passwordEncoder.encode("kayone"));
			User u2 = new User("Hans", "im Glück", r1, "hansi-happiness", passwordEncoder.encode("stycks"));
			User u3 = new User("Spongebog", "Schwammkopf", r3, "spongi", passwordEncoder.encode("patrick"));
			User u4 = new User("John", "Doe", r2, "durak", passwordEncoder.encode("jonny"));

			userRepo.save(user0);
			userRepo.save(u1);
			userRepo.save(u2);
			userRepo.save(u3);
			userRepo.save(u4);

			// -----------------------------//
			// ------------- Address -------//
			// -----------------------------//
			Address add0 = new Address("Kein Weg", 1, 24111, "Bei Berlin City", "weißt-du-doch-nicht-land");
			addRepo.save(add0);
			Address a1 = new Address("Olshausenstr.", 111, 24107, "Kiel", "Deutschland");
			addRepo.save(a1);
			Address a2 = new Address("Nebelungen-Weg", 007, 98745, "Nimmerland", "Peter-Pan-Land");
			addRepo.save(a2);
			Address a3 = new Address("Nice Ave.", 3457, 15341, "New York", "Süd-Afrika");
			addRepo.save(a3);
			Address a4 = new Address("Beim-dicken-Michi", 1, 98745, "Moskau", "Russland");
			addRepo.save(a4);

			// -----------------------------//
			// ------------- Project -------//
			// -----------------------------//
			Project pro0 = new Project("pro0", null, null, null, Status.NO_STATUS, 0, "root", null, null, null, add0,
					org0);
			Project p1 = new Project("Burj Khalifa2", "steht direkt daneben", "2010-02-07", "2021-06-01", Status.OK,
					234578900, "Die den anderen Turm auch gemacht haben", null, null, null, a3, org3);
			Project p2 = new Project("Berliner Flughafen xD", "Morgen ist es soweit", "2010-12-28", "2015-01-01",
					Status.OPEN, 1300500000, "Nicht die vom Burj Khalifa", null, null, null, a2, org5);
			proRepo.save(pro0);
			proRepo.save(p1);
			proRepo.save(p2);

			// ------------------------------//
			// ------------- Contract -------//
			// ------------------------------//
			Contract con0 = new Contract("con0", null, Status.NO_STATUS, null, null, organisations, pro0);
			Contract con1 = new Contract("Vertrag für das neue Fenster", "auf nachfrage", Status.DENY, "Jörg", "Peter",
					organisations1, p1);
			Contract con2 = new Contract("Haus-Restaurierung", "eilauftrag", Status.OK, "Microsoft INC", "Apple INC",
					organisations2, p2);
			conRepo.save(con0);
			conRepo.save(con1);
			conRepo.save(con2);

			// ------------------------------//
			// ------------- BillingUnit ----//
			// ------------------------------//
			BillingUnit billUnit0 = new BillingUnit(null, null, null, null, null, 0, 0, con0);
			billUnitRepo.save(billUnit0);
			// ------------------------------//
			// ------------- BillingItem ----//
			// ------------------------------//
			BillingItem billItem0 = new BillingItem("bill0", 0, null, Status.NO_STATUS, 0, null, 0, null, null,
					billUnit0, null);

			billItemRepo.save(billItem0);
		}
	}
}
