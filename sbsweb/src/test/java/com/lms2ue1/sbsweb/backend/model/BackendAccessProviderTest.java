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
    public void init() {
	closeable = MockitoAnnotations.openMocks(this);

	// Initialize fields
	organisation1 = new Organisation("Fritz Mï¿½ller GmbH");
	organisation1.setId(1L);
	organisation2 = new Organisation("Fritz Mï¿½ller-Schulz GmbH");
	organisation2.setId(2L);
	address1 = new Address("Main Street", 1337, 14, "NY City", "Deutschland");
	address1.setAddressId(1L);
	address2 = new Address("2nd Street", 42, 15, "GBR City", "France");
	address1.setAddressId(2L);
	project1 = new Project("Burj Khalifa2", "steht direkt daneben", "2010-02-07", "2021-06-01", Status.OK,
		234578900, "Die den anderen Turm auch gemacht haben", null, null, null, address1, organisation1);
	project1.setId(1L);
	project2 = new Project("Berliner Flughafen xD", "Morgen ist es soweit", "2010-12-28", "2015-01-01", Status.OPEN,
		1300500000, "Nicht die vom Burj Khalifa", null, null, null, address2, organisation2);
	project2.setId(2L);
	contract1 = new Contract("Baby beruhigen", "Es schreit.", Status.NO_STATUS, "Nanny", "Mutter", List.of(),
		project1);
	contract1.setId(1L);
	contract2 = new Contract("Kosten klein halten", "Teuer", null, null, null, List.of(organisation2), project2);
	contract2.setId(2L);
	billingUnit1 = new BillingUnit("sd", null, "kg", "1973", "2001", 2.1, 7, contract1);
	billingUnit1.setId(1L);
	billingUnit2 = new BillingUnit("sssdad", null, "ï¿½", "15999", "2201", 1, 0, contract2);
	billingUnit2.setId(2L);
	billingItem1 = new BillingItem("Heizung montieren", 2, "Heizko B7-2 fensternah einbauen.", Status.OPEN, 0, null,
		7, null, null, billingUnit1, null);
	billingItem1.setId(1L);
	billingItem2 = new BillingItem("Fenster einbauen", 0, null, null, 99, null, 0, null, null, billingUnit2, null);
	billingItem2.setId(2L);
	role1 = new Role("Bauherr", List.of(), List.of(), List.of(), organisation1, false);
	role1.setId(1L);
	role2 = new Role("Handwerker", null, null, null, organisation2, false);
	role2.setId(2L);
	user1 = new User("Fritz", "Müller", null, "f.mueller", "fritzi");
	user1.setId(1L);
	user2 = new User("Hans", "Schulz", null, "hs", "hansss");
	user2.setId(2L);

	////// Standard mock behavior
	// Auth (most methods called with failUsername automatically return false)
	when(auth.isSysAdmin(rootUsername)).thenReturn(true);
	when(auth.isAdmin(rootUsername)).thenReturn(true);
	when(auth.getOrgAdminID(rootUsername)).thenReturn(0L);
	when(auth.getOrgAdminID(failUsername)).thenReturn(null);
	when(auth.canManageUser(rootUsername, user1.getId())).thenReturn(true);
	when(auth.canManageUser(rootUsername, user2.getId())).thenReturn(true);
	when(auth.checkOrganisation(rootUsername, organisation1.getId())).thenReturn(true);
	when(auth.checkOrganisation(rootUsername, organisation2.getId())).thenReturn(true);
	when(auth.checkAddress(rootUsername, address1.getId())).thenReturn(true);
	when(auth.checkAddress(rootUsername, address2.getId())).thenReturn(true);
	when(auth.checkProject(rootUsername, project1.getId())).thenReturn(true);
	when(auth.checkProject(rootUsername, project2.getId())).thenReturn(true);
	when(auth.checkContract(rootUsername, contract1.getId())).thenReturn(true);
	when(auth.checkContract(rootUsername, contract2.getId())).thenReturn(true);
	when(auth.checkBillingUnit(rootUsername, billingUnit1.getId())).thenReturn(true);
	when(auth.checkBillingUnit(rootUsername, billingUnit2.getId())).thenReturn(true);
	when(auth.checkBillingItem(rootUsername, billingItem1.getId())).thenReturn(true);
	when(auth.checkBillingItem(rootUsername, billingItem2.getId())).thenReturn(true);
	// Repositories
	when(organisations.findById(organisation1.getId())).thenReturn(Optional.of(organisation1));
	when(organisations.findById(organisation2.getId())).thenReturn(Optional.of(organisation2));
	when(addresses.findById(address1.getId())).thenReturn(Optional.of(address1));
	when(addresses.findById(address2.getId())).thenReturn(Optional.of(address2));
	when(projects.findById(project1.getId())).thenReturn(Optional.of(project1));
	when(projects.findById(project2.getId())).thenReturn(Optional.of(project2));
	when(contracts.findById(contract1.getId())).thenReturn(Optional.of(contract1));
	when(contracts.findById(contract2.getId())).thenReturn(Optional.of(contract2));
	when(billingUnits.findById(billingUnit1.getId())).thenReturn(Optional.of(billingUnit1));
	when(billingUnits.findById(billingUnit2.getId())).thenReturn(Optional.of(billingUnit2));
	when(billingItems.findById(billingItem1.getId())).thenReturn(Optional.of(billingItem1));
	when(billingItems.findById(billingItem2.getId())).thenReturn(Optional.of(billingItem2));
	when(roles.findById(role1.getId())).thenReturn(Optional.of(role1));
	when(roles.findById(role2.getId())).thenReturn(Optional.of(role2));
	when(users.findById(user1.getId())).thenReturn(Optional.of(user1));
	when(users.findById(user2.getId())).thenReturn(Optional.of(user2));
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
    public void testRemoveOrganisation() {
	Long id1 = organisation1.getId();
	assertDoesNotThrow(() -> BAP.removeOrganisation(rootUsername, id1), "Root couldn't remove the organisation!");
	verify(organisations).deleteById(id1);
	Long id2 = organisation2.getId();
	assertThrows(AuthenticationException.class, () -> BAP.removeOrganisation(failUsername, id2),
		"Fail removed the organisation!");
	verify(organisations, never()).deleteById(id2);
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
	assertDoesNotThrow(() -> BAP.addUser(rootUsername, user1), "Root couldn't add the user!");
	verify(users).save(user1);
	assertThrows(AuthenticationException.class, () -> BAP.addUser(failUsername, user2), "Fail added the user!");
	verify(users, never()).save(user2);
    }

    @Test
    public void testRemoveUser() {
	Long id1 = user1.getId();
	assertDoesNotThrow(() -> BAP.removeUser(rootUsername, id1), "Root couldn't remove the user!");
	verify(users).deleteById(id1);
	Long id2 = user2.getId();
	assertThrows(AuthenticationException.class, () -> BAP.removeUser(failUsername, id2), "Fail removed the user!");
	verify(users, never()).deleteById(id2);
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
	assertDoesNotThrow(() -> BAP.addRole(rootUsername, role1), "Root couldn't add the role!");
	verify(roles).save(role1);
	assertThrows(AuthenticationException.class, () -> BAP.addRole(failUsername, role2), "Fail added the role!");
	verify(roles, never()).save(role2);
    }

    @Test
    public void testRemoveRole() {
	Long id1 = role1.getId();
	assertDoesNotThrow(() -> BAP.removeRole(rootUsername, id1), "Root couldn't remove the role!");
	verify(roles).deleteById(id1);
	Long id2 = role2.getId();
	assertThrows(AuthenticationException.class, () -> BAP.removeRole(failUsername, id2), "Fail removed the role!");
	verify(roles, never()).deleteById(id2);
    }

    @Test
    public void testUpdateRole() {
	// TODO
//	BAP.updateRole(rootUsername, role1.getId(), role2);
	assertTrue(false);
    }

    //// Get by ID

    @Test
    public void testGetProjectById() throws AuthenticationException {
	Long id = project1.getId();
	assertDoesNotThrow(() -> BAP.getProjectById(rootUsername, id), "Root couldn't find a project by ID!");
	assertEquals(project1, BAP.getProjectById(rootUsername, id), "A different project was found!");
	assertThrows(AuthenticationException.class, () -> BAP.getProjectById(failUsername, id),
		"Fail found a project by ID!");
    }

    @Test
    public void testGetContractById() throws AuthenticationException {
	Long id = contract1.getId();
	assertDoesNotThrow(() -> BAP.getContractById(rootUsername, id), "Root couldn't find a contract by ID!");
	assertEquals(contract1, BAP.getContractById(rootUsername, id), "A different contract was found!");
	assertThrows(AuthenticationException.class, () -> BAP.getContractById(failUsername, id),
		"Fail found a contract by ID!");
    }

    @Test
    public void testGetBillingUnitById() throws AuthenticationException {
	Long id = billingUnit1.getId();
	assertDoesNotThrow(() -> BAP.getBillingUnitById(rootUsername, id), "Root couldn't find a billing unit by ID!");
	assertEquals(billingUnit1, BAP.getBillingUnitById(rootUsername, id), "A different billing unit was found!");
	assertThrows(AuthenticationException.class, () -> BAP.getBillingUnitById(failUsername, id),
		"Fail found a billing unit by ID!");
    }

    @Test
    public void testGetBillingItemById() throws AuthenticationException {
	Long id = billingItem1.getId();
	assertDoesNotThrow(() -> BAP.getBillingItemById(rootUsername, id), "Root couldn't find a billing item by ID!");
	assertEquals(billingItem1, BAP.getBillingItemById(rootUsername, id), "A different billing item was found!");
	assertThrows(AuthenticationException.class, () -> BAP.getBillingItemById(failUsername, id),
		"Fail found a billing item by ID!");
    }

    @Test
    public void testGetOrganisationById() throws AuthenticationException {
	Long id = organisation1.getId();
	assertDoesNotThrow(() -> BAP.getOrganisationById(rootUsername, id),
		"Root couldn't find an organisation by ID!");
	assertEquals(organisation1, BAP.getOrganisationById(rootUsername, id), "A different organisation was found!");
	assertThrows(AuthenticationException.class, () -> BAP.getOrganisationById(failUsername, id),
		"Fail found an organisation by ID!");
    }

    //// Get all accessible
}
