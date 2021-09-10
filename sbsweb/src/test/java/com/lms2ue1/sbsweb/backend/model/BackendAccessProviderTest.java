package com.lms2ue1.sbsweb.backend.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lms2ue1.sbsweb.backend.repository.*;
import com.lms2ue1.sbsweb.backend.security.AuthorisationCheck;

@SpringBootTest
public class BackendAccessProviderTest {

    //// Mocks

    @MockBean
    private AddressRepository addresses;
    @MockBean
    private ProjectRepository projects;
    @MockBean
    private ContractRepository contracts;
    @MockBean
    private BillingUnitRepository billingUnits;
    @MockBean
    private BillingItemRepository billingItems;
    @MockBean
    private OrganisationRepository organisations;
    @MockBean
    private UserRepository users;
    @MockBean
    private RoleRepository roles;
    @Mock
    private AuthorisationCheck auth;
    @InjectMocks
    private BackendAccessProvider BAP;

    private AutoCloseable closeable;

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
    private String rootUsername = "root";
    private String failUsername = null;

    @BeforeEach
    public void initFields() {
	closeable = MockitoAnnotations.openMocks(this);
	when(auth.isSysAdmin(rootUsername)).thenReturn(true);
	when(auth.isSysAdmin(failUsername)).thenReturn(false);
	when(auth.getOrgAdminID(rootUsername)).thenReturn(0L);
	when(auth.getOrgAdminID(failUsername)).thenReturn(null);

	organisation1 = new Organisation("Fritz Mï¿½ller GmbH");
	organisation2 = new Organisation("Fritz Mï¿½ller-Schulz GmbH");
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
	billingUnit2 = new BillingUnit("sssdad", null, "ï¿½", "15999", "2201", 1, 0, contract2);
	billingItem1 = new BillingItem("Heizung montieren", 2, "Heizko B7-2 fensternah einbauen.", Status.OPEN, 0, null,
		7, null, null, billingUnit1, null);
	billingItem2 = new BillingItem("Fenster einbauen", 0, null, null, 99, null, 0, null, null, billingUnit2, null);
	role1 = new Role("Bauherr", List.of(), List.of(), List.of(), organisation1, false);
	role2 = new Role("Handwerker", null, null, null, organisation2, false);
	user1 = new User("Fritz", "Müller", null, "f.mueller", "fritzi");
	user2 = new User("Hans", "Schulz", null, "hs", "hansss");
    }

    @AfterEach
    public void releaseMocks() throws Exception {
	closeable.close();
    }

    //// Organisation

    @Test
    public void testAddOrganisation() {
	assertDoesNotThrow(() -> BAP.addOrganisation(rootUsername, organisation1),
		"Root couldn't add the organisation!");
	verify(organisations).save(organisation1);
	assertThrows(AuthenticationException.class, () -> BAP.addOrganisation(failUsername, organisation2),
		"Fail added the organisation!");
	verify(organisations, never()).save(organisation2);
    }

    @Test
    public void testRemoveOrganisationRoot() {
	Long id = organisation1.getId();
	assertDoesNotThrow(() -> BAP.removeOrganisation(rootUsername, id), "Root couldn't remove the organisation!");
	verify(organisations).deleteById(id);
    }

    @Test
    public void testRemoveOrganisationFail() {
	Long id = organisation2.getId();
	assertThrows(AuthenticationException.class, () -> BAP.removeOrganisation(failUsername, id),
		"Fail removed the organisation!");
	verify(organisations, never()).deleteById(id);
    }

    @Test
    public void testUpdateOrganisation() {
	assertDoesNotThrow(() -> BAP.updateOrganisation(rootUsername, organisation1.getId(), organisation2),
		"Root couldn't update the organisation!");
	assertTrue(false); // TODO
    }

    //// User

    @Test
    public void testAddUser() {
//	BAP.addUser(rootUsername, user1);
	verify(users).save(user1);
    }

    @Test
    public void testRemoveUser() {
//	BAP.removeUser(rootUsername, user1.getId());
	verify(users).deleteById(user1.getId());
    }

    @Test
    public void testUpdateUser() {
	// TODO
//	BAP.updateUser(rootUsername, user1.getId(), user2);
	assertTrue(false);
    }

    //// Role

    @Test
    public void testAddRole() {
//	BAP.addRole(rootUsername, role1);
	verify(roles).save(role1);
    }

    @Test
    public void testRemoveRole() {
//	BAP.removeRole(rootUsername, role1.getId());
	verify(roles).deleteById(role1.getId());
    }

    @Test
    public void testUpdateRole() {
	// TODO
//	BAP.updateRole(rootUsername, role1.getId(), role2);
	assertTrue(false);
    }

    //// Get by ID

    @Test
    public void testGetProjectById() {
	Long id = project1.getId();
	when(projects.findById(id)).thenReturn(Optional.of(project1));
	assertEquals(project1, BAP.getProjectById(rootUsername, id), "The project wasn't found!");
	assertThrows(AuthenticationException.class, () -> BAP.getProjectById(failUsername, id),
		"An unauthorized user was granted access!");
    }

    @Test
    public void testGetContractById() {
	Long id = contract1.getId();
	when(contracts.findById(id)).thenReturn(Optional.of(contract1));
	assertEquals(contract1, BAP.getContractById(rootUsername, id), "The contract wasn't found!");
	assertThrows(AuthenticationException.class, () -> BAP.getContractById(failUsername, id),
		"An unauthorized user was granted access!");
    }

    @Test
    public void testGetBillingUnitById() {
	Long id = billingUnit1.getId();
	when(billingUnits.findById(id)).thenReturn(Optional.of(billingUnit1));
	assertEquals(billingUnit1, BAP.getBillingUnitById(rootUsername, id), "The billing unit wasn't found!");
	assertThrows(AuthenticationException.class, () -> BAP.getBillingUnitById(failUsername, id),
		"An unauthorized user was granted access!");
    }

    @Test
    public void testGetBillingItemById() {
	Long id = billingItem1.getId();
	when(billingItems.findById(id)).thenReturn(Optional.of(billingItem1));
	assertEquals(billingItem1, BAP.getBillingItemById(rootUsername, id), "The billing item wasn't found!");
	assertThrows(AuthenticationException.class, () -> BAP.getBillingItemById(failUsername, id),
		"An unauthorized user was granted access!");
    }

    @Test
    public void testGetOrganisationById() {
	Long id = organisation1.getId();
	when(organisations.findById(id)).thenReturn(Optional.of(organisation1));
	assertEquals(organisation1, BAP.getOrganisationById(rootUsername, id), "The organisation wasn't found!");
	assertThrows(AuthenticationException.class, () -> BAP.getOrganisationById(failUsername, id),
		"An unauthorized user was granted access!");
    }

    @Test
    public void testGetUserById() {
	Long id = user1.getId();
	when(users.findById(id)).thenReturn(Optional.of(user1));
	assertEquals(user1, BAP.getUserById(rootUsername, id), "The user wasn't found!");
	assertThrows(AuthenticationException.class, () -> BAP.getUserById(failUsername, id),
		"An unauthorized user was granted access!");
    }

    //// Get all accessible
}
