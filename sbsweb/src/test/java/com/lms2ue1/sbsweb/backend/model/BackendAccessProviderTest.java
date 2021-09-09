package com.lms2ue1.sbsweb.backend.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lms2ue1.sbsweb.backend.repository.*;

@SpringBootTest
public class BackendAccessProviderTest {

    private static final BackendAccessProvider BAP = BackendAccessProvider.getInstance();

    @Autowired
    private AddressRepository addresses;
    @Autowired
    private ProjectRepository projects;
    @Autowired
    private ContractRepository contracts;
    @Autowired
    private BillingUnitRepository billingUnits;
    @Autowired
    private BillingItemRepository billingItems;
    @Autowired
    private OrganisationRepository organisations;
    @Autowired
    private UserRepository users;
    @Autowired
    private RoleRepository roles;

    @BeforeEach
    public void resetRepos() {
	// Clear repos
	billingItems.deleteAll();
	billingUnits.deleteAll();
	contracts.deleteAll();
	projects.deleteAll();
	addresses.deleteAll();
	users.deleteAll();
	roles.deleteAll();
	organisations.deleteAll();

	// Init fields
	organisation1 = new Organisation("Fritz Müller GmbH");
	organisation2 = new Organisation("Fritz Müller-Schulz GmbH");
	address1 = new Address("Main Street", 1337, 14, "NY City", "Deutschland");
	address2 = new Address("2nd Street", 42, 15, "GBR City", "France");
	project1 = new Project("Burj Khalifa2", "steht direkt daneben", "2010-02-07", "2021-06-01", Status.OK,
		234578900, "Die den anderen Turm auch gemacht haben", null, null, null, address1, organisation1);
	project2 = new Project("Berliner Flughafen xD", "Morgen ist es soweit", "2010-12-28", "2015-01-01", Status.OPEN,
		1300500000, "Nicht die vom Burj Khalifa", null, null, null, address2, organisation2);
	contract1 = new Contract("Baby beruhigen", "Es schreit.", Status.NO_STATUS, "Nanny", "Mutter", List.of(),
		project1);
	contract2 = new Contract("Kosten klein halten", "Teuer", null, null, null, List.of(organisation2), project2);
	billingUnit1 = new BillingUnit("sd", null, "kg", "1973", "2001", 2.1, 7, contract1);
	billingUnit2 = new BillingUnit("sssdad", null, "€", "15999", "2201", 1, 0, contract2);
	billingItem1 = new BillingItem("Heizung montieren", 2, "Heizko B7-2 fensternah einbauen.", Status.OPEN, 0, null,
		7, null, null, billingUnit1, null);
	billingItem2 = new BillingItem("Fenster einbauen", 0, null, null, 99, null, 0, null, null, billingUnit2, null);
	role1 = new Role("Bauherr", List.of(), List.of(), List.of(), organisation1);
	role2 = new Role("Handwerker", null, null, null, organisation2);
	user1 = new User("Fritz", "Müller", null, "f.mueller", "fritzi");
	user2 = new User("Hans", "Schulz", null, "hs", "hansss");
    }

    //// Fields to use per test
    private Address address1;
    private Address address2;
    private Project project1;
    private Project project2;
    private Contract contract1;
    private Contract contract2;
    private BillingUnit billingUnit1;
    private BillingUnit billingUnit2;
    private BillingItem billingItem1;
    private BillingItem billingItem2;
    private Organisation organisation1;
    private Organisation organisation2;
    private User user1;
    private User user2;
    private Role role1;
    private Role role2;

    //// Organisation

    @Test
    public void testAddOrganisation() {
	String username = "root";
	BAP.addOrganisation(username, organisation1);
	assertDoesNotThrow(() -> organisations.findById(organisation1.getId()).get(), "Organisation wasn't added!");
	assertEquals(organisations.findById(organisation1.getId()).get(), organisation1,
		"Another organisation with same id is present!");
    }

    @Test
    public void testRemoveOrganisation() {
	// Add
	String username = "root";
	BAP.addOrganisation(username, organisation1);
	assertDoesNotThrow(() -> organisations.findById(organisation1.getId()).get(), "Organisation wasn't added!");
	// Remove
	BAP.removeOrganisation(username, organisation1.getId());
	assertTrue(organisations.findById(organisation1.getId()).isEmpty(), "Organisation wasn't removed!");
    }

    @Test
    public void testUpdateOrganisation() {
	// Add
	String username = "root";
	BAP.addOrganisation(username, organisation1);
	assertDoesNotThrow(() -> organisations.findById(organisation1.getId()).get(), "Organisation wasn't added!");
	// Update
	BAP.updateOrganisation(username, organisation1.getId(), organisation2);
	assertThrows(NoSuchElementException.class, () -> organisations.findById(organisation1.getId()),
		"Old organisation wasn't removed");
	assertDoesNotThrow(() -> organisations.findById(organisation2.getId()).get(), "Organisation wasn't updated!");
	assertEquals(organisations.findById(organisation2.getId()).get(), organisation2,
		"Another organisation with same id is present!");
    }

    //// User

    @Test
    public void testAddUser() {
	String username = "root";
	BAP.addUser(username, user1);
	assertDoesNotThrow(() -> users.findById(user1.getId()).get(), "User wasn't added!");
	assertEquals(users.findById(user1.getId()).get(), user1, "Another user with same id is present!");
    }

    @Test
    public void testRemoveUser() {
	// Add
	String username = "root";
	BAP.addUser(username, user1);
	assertDoesNotThrow(() -> users.findById(user1.getId()).get(), "User wasn't added!");
	// Remove
	BAP.removeUser(username, user1.getId());
	assertTrue(users.findById(user1.getId()).isEmpty(), "User wasn't removed!");
    }

    @Test
    public void testUpdateUser() {
	// Add
	String username = "root";
	BAP.addUser(username, user1);
	assertDoesNotThrow(() -> users.findById(user1.getId()).get(), "User wasn't added!");
	// Update
	BAP.updateUser(username, user1.getId(), user2);
	assertThrows(NoSuchElementException.class, () -> users.findById(user1.getId()), "Old user wasn't removed");
	assertDoesNotThrow(() -> users.findById(user2.getId()).get(), "User wasn't updated!");
	assertEquals(users.findById(user2.getId()).get(), user2, "Another user with same id is present!");
    }

    //// Role

    @Test
    public void testAddRole() {
	String username = "root";
	BAP.addRole(username, role1);
	assertDoesNotThrow(() -> roles.findById(role1.getId()).get(), "Role wasn't added!");
	assertEquals(roles.findById(role1.getId()).get(), role1, "Another role with same id is present!");
    }

    @Test
    public void testRemoveRole() {
	// Add
	String username = "root";
	BAP.addRole(username, role1);
	assertDoesNotThrow(() -> roles.findById(role1.getId()).get(), "Role wasn't added!");
	// Remove
	BAP.removeRole(username, role1.getId());
	assertTrue(roles.findById(role1.getId()).isEmpty(), "Role wasn't removed!");
    }

    @Test
    public void testUpdateRole() {
	// Add
	String username = "root";
	BAP.addRole(username, role1);
	assertDoesNotThrow(() -> roles.findById(role1.getId()).get(), "Role wasn't added!");
	// Update
	BAP.updateRole(username, role1.getId(), role2);
	assertThrows(NoSuchElementException.class, () -> roles.findById(role1.getId()), "Old role wasn't removed");
	assertDoesNotThrow(() -> roles.findById(role2.getId()).get(), "Role wasn't updated!");
	assertEquals(roles.findById(role2.getId()).get(), role2, "Another role with same id is present!");
    }

    @Test
    public void testGetProjectById() {
	// Add
	String username = "root";
	organisations.save(organisation1);
	addresses.save(address1);
	projects.save(project1);
	assertEquals(projects.findById(project1.getId()).get(), null);//BAP.getProjectById(username, project1.getId()),
//		"The project wasn't found!");
    }
}
