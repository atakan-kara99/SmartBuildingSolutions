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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lms2ue1.sbsweb.backend.repository.*;
import com.lms2ue1.sbsweb.backend.security.AuthorisationCheck;

@SpringBootTest
public class BackendAccessProviderTest {

    //// Mocks

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
    @MockBean
    private StatusRepository stati;
    @Mock
    private AuthorisationCheck auth;
    @InjectMocks
    private BackendAccessProvider BAP;

    private AutoCloseable closeable;

    //// Fields to use per test
    private Status noStatus;
    private Status open;
    private Status ok;
    private Status deny;
    private Status status1;
    private Status status2;
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
	ok = new Status("OK", null);
	ok.setId(-1L);
	deny = new Status("DENY", null);
	deny.setId(-2L);
	open = new Status("OPEN", List.of(ok, deny));
	open.setId(-3L);
	noStatus = new Status("NO_STATUS", List.of(open));
	noStatus.setId(-4L);
	status1 = new Status("Status1", null);
	status1.setId(1L);
	status2 = new Status("Status2", null);
	status2.setId(2L);
	organisation1 = new Organisation("Fritz M�ller GmbH");
	organisation1.setId(1L);
	organisation2 = new Organisation("Fritz M�ller-Schulz GmbH");
	organisation2.setId(2L);
	address1 = new Address("Main Street", 1337, 14, "NY City", "Deutschland");
	address2 = new Address("2nd Street", 42, 15, "GBR City", "France");
	project1 = new Project("Burj Khalifa2", "steht direkt daneben", "2010-02-07", "2021-06-01", 234578900,
		"Die den anderen Turm auch gemacht haben", null, null, null, address1, organisation1);
	project1.setInternID(1L);
	project2 = new Project("Berliner Flughafen xD", "Morgen ist es soweit", "2010-12-28", "2015-01-01", 1300500000,
		"Nicht die vom Burj Khalifa", null, null, null, address2, organisation2);
	project2.setInternID(2L);
	contract1 = new Contract("Baby beruhigen", "Es schreit.", "Nanny", "Mutter", List.of(), project1);
	contract1.setInternID(1L);
	contract2 = new Contract("Kosten klein halten", "Teuer", null, null, List.of(organisation2), project2);
	contract2.setInternID(2L);
	billingUnit1 = new BillingUnit("sd", null, "kg", "1973", "2001", 2.1, 7, contract1);
	billingUnit1.setInternID(1L);
	billingUnit2 = new BillingUnit("sssdad", null, "�", "15999", "2201", 1, 0, contract2);
	billingUnit2.setInternID(2L);
	billingItem1 = new BillingItem("Heizung montieren", 2, "Heizko B7-2 fensternah einbauen.", noStatus, 0, null, 7,
		null, null, billingUnit1, null);
	billingItem1.setInternID(1L);
	billingItem2 = new BillingItem("Fenster einbauen", 0, null, noStatus, 99, null, 0, null, null, billingUnit2,
		null);
	billingItem2.setInternID(2L);
	role1 = new Role("Bauherr", List.of(), List.of(), List.of(), organisation1, false);
	role1.setId(1L);
	role2 = new Role("Handwerker", null, null, null, organisation2, false);
	role2.setId(2L);
	user1 = new User("Fritz", "M�ller", null, "f.mueller", "fritzi");
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
	when(auth.checkProject(rootUsername, project1.getInternID())).thenReturn(true);
	when(auth.checkProject(rootUsername, project2.getInternID())).thenReturn(true);
	when(auth.checkContract(rootUsername, contract1.getInternID())).thenReturn(true);
	when(auth.checkContract(rootUsername, contract2.getInternID())).thenReturn(true);
	when(auth.checkBillingUnit(rootUsername, billingUnit1.getInternID())).thenReturn(true);
	when(auth.checkBillingUnit(rootUsername, billingUnit2.getInternID())).thenReturn(true);
	when(auth.checkBillingItem(rootUsername, billingItem1.getInternID())).thenReturn(true);
	when(auth.checkBillingItem(rootUsername, billingItem2.getInternID())).thenReturn(true);
	// Repositories
	when(stati.findById(status1.getId())).thenReturn(Optional.of(status1));
	when(stati.findById(status2.getId())).thenReturn(Optional.of(status2));
	when(stati.findByNameIgnoreCase(status1.getName())).thenReturn(status1);
	when(stati.findByNameIgnoreCase(status2.getName())).thenReturn(status2);
	when(organisations.findById(organisation1.getId())).thenReturn(Optional.of(organisation1));
	when(organisations.findById(organisation2.getId())).thenReturn(Optional.of(organisation2));
	when(projects.findById(project1.getInternID())).thenReturn(Optional.of(project1));
	when(projects.findById(project2.getInternID())).thenReturn(Optional.of(project2));
	when(contracts.findById(contract1.getInternID())).thenReturn(Optional.of(contract1));
	when(contracts.findById(contract2.getInternID())).thenReturn(Optional.of(contract2));
	when(billingUnits.findById(billingUnit1.getInternID())).thenReturn(Optional.of(billingUnit1));
	when(billingUnits.findById(billingUnit2.getInternID())).thenReturn(Optional.of(billingUnit2));
	when(billingItems.findById(billingItem1.getInternID())).thenReturn(Optional.of(billingItem1));
	when(billingItems.findById(billingItem2.getInternID())).thenReturn(Optional.of(billingItem2));
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
		"An unauthorized user added the organisation!");
	verify(organisations, never()).save(organisation2);
    }

    @Test
    public void testRemoveOrganisation() {
	Long id1 = organisation1.getId();
	assertDoesNotThrow(() -> BAP.removeOrganisation(rootUsername, id1), "Root couldn't remove the organisation!");
	verify(organisations).deleteById(id1);
	Long id2 = organisation2.getId();
	assertThrows(AuthenticationException.class, () -> BAP.removeOrganisation(failUsername, id2),
		"An unauthorized user removed the organisation!");
	verify(organisations, never()).deleteById(id2);
    }

    @Test
    public void testUpdateOrganisation() {
	doAnswer(new Answer<Void>() {
	    @Override
	    public Void answer(InvocationOnMock invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		if (args != null && args.length > 1 && args[0] != null && args[1] != null) {
		    organisation1 = (Organisation) args[0];
		    organisation1.setName(organisation2.getName());
		}
		return null;
	    }
	}).when(organisations).save(organisation1);
	assertDoesNotThrow(() -> BAP.updateOrganisation(rootUsername, organisation1.getId(), organisation2),
		"Root couldn't update the organisation!");
	verify(organisations).save(organisation1);
    }

    @Test
    public void testFailUpdateOrganisation() {
	assertThrows(AuthenticationException.class,
		() -> BAP.updateOrganisation(failUsername, organisation1.getId(), organisation2),
		"An unauthorized user updated the organisation!");
	verify(organisations, never()).save(organisation1);
    }

    //// User

    @Test
    public void testAddUser() {
	assertDoesNotThrow(() -> BAP.addUser(rootUsername, user1), "Root couldn't add the user!");
	verify(users).save(user1);
	assertThrows(AuthenticationException.class, () -> BAP.addUser(failUsername, user2),
		"An unauthorized user added the user!");
	verify(users, never()).save(user2);
    }

    @Test
    public void testRemoveUser() {
	Long id1 = user1.getId();
	assertDoesNotThrow(() -> BAP.removeUser(rootUsername, id1), "Root couldn't remove the user!");
	verify(users).deleteById(id1);
	Long id2 = user2.getId();
	assertThrows(AuthenticationException.class, () -> BAP.removeUser(failUsername, id2),
		"An unauthorized user removed the user!");
	verify(users, never()).deleteById(id2);
    }

    @Test
    public void testUpdateUser() {
	doAnswer(new Answer<Void>() {
	    @Override
	    public Void answer(InvocationOnMock invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		if (args != null && args.length > 1 && args[0] != null && args[1] != null) {
		    user1 = (User) args[0];
		    user1.setForename(user2.getForename());
		    user1.setLastname(user2.getLastname());
		    user1.setUsername(user2.getUsername());
		    user1.setPassword(user2.getPassword());
		    user1.setRole(user2.getRole());

		}
		return null;
	    }
	}).when(users).save(user1);
	assertDoesNotThrow(() -> BAP.updateUser(rootUsername, user1.getId(), user2), "Root couldn't update the user!");
	verify(users).save(user1);
    }

    @Test
    public void testFailUpdateUser() {
	assertThrows(AuthenticationException.class, () -> BAP.updateUser(failUsername, user1.getId(), user2),
		"An unauthorized user updated the user!");
	verify(users, never()).save(user1);
    }

    //// Role

    @Test
    public void testAddRole() {
	assertDoesNotThrow(() -> BAP.addRole(rootUsername, role1), "Root couldn't add the role!");
	verify(roles).save(role1);
	assertThrows(AuthenticationException.class, () -> BAP.addRole(failUsername, role2),
		"An unauthorized user added the role!");
	verify(roles, never()).save(role2);
    }

    @Test
    public void testRemoveRole() {
	Long id1 = role1.getId();
	assertDoesNotThrow(() -> BAP.removeRole(rootUsername, id1), "Root couldn't remove the role!");
	verify(roles).deleteById(id1);
	Long id2 = role2.getId();
	assertThrows(AuthenticationException.class, () -> BAP.removeRole(failUsername, id2),
		"An unauthorized user removed the role!");
	verify(roles, never()).deleteById(id2);
    }

    @Test
    public void testUpdateRole() {
	doAnswer(new Answer<Void>() {
	    @Override
	    public Void answer(InvocationOnMock invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		if (args != null && args.length > 1 && args[0] != null && args[1] != null) {
		    role1 = (Role) args[0];
		    role1.setName(role2.getName());
		    role1.setManageUser(role2.isManageUser());
		    role1.setName(role2.getName());
		    role1.setProjects(role2.getProjects());
		    role1.setContracts(role2.getContracts());
		    role1.setBillingItems(role2.getBillingItems());

		}
		return null;
	    }
	}).when(roles).save(role1);
	assertDoesNotThrow(() -> BAP.updateRole(rootUsername, role1.getId(), role2), "Root couldn't update the role!");
	verify(roles).save(role1);
    }

    @Test
    public void testFailUpdateRole() {
	assertThrows(AuthenticationException.class, () -> BAP.updateRole(failUsername, role1.getId(), role2),
		"An unauthorized user updated the role!");
	verify(roles, never()).save(role1);
    }

    //// Get by ID

    @Test
    public void testGetProjectById() throws AuthenticationException {
	Long id = project1.getInternID();
	assertDoesNotThrow(() -> BAP.getProjectById(rootUsername, id), "Root couldn't find a project by ID!");
	assertEquals(project1, BAP.getProjectById(rootUsername, id), "A different project was found!");
	assertThrows(AuthenticationException.class, () -> BAP.getProjectById(failUsername, id),
		"An unauthorized user found a project by ID!");
    }

    @Test
    public void testGetContractById() throws AuthenticationException {
	Long id = contract1.getInternID();
	assertDoesNotThrow(() -> BAP.getContractById(rootUsername, id), "Root couldn't find a contract by ID!");
	assertEquals(contract1, BAP.getContractById(rootUsername, id), "A different contract was found!");
	assertThrows(AuthenticationException.class, () -> BAP.getContractById(failUsername, id),
		"An unauthorized user found a contract by ID!");
    }

    @Test
    public void testGetBillingUnitById() throws AuthenticationException {
	Long id = billingUnit1.getInternID();
	assertDoesNotThrow(() -> BAP.getBillingUnitById(rootUsername, id), "Root couldn't find a billing unit by ID!");
	assertEquals(billingUnit1, BAP.getBillingUnitById(rootUsername, id), "A different billing unit was found!");
	assertThrows(AuthenticationException.class, () -> BAP.getBillingUnitById(failUsername, id),
		"An unauthorized user found a billing unit by ID!");
    }

    @Test
    public void testGetBillingItemById() throws AuthenticationException {
	Long id = billingItem1.getInternID();
	assertDoesNotThrow(() -> BAP.getBillingItemById(rootUsername, id), "Root couldn't find a billing item by ID!");
	assertEquals(billingItem1, BAP.getBillingItemById(rootUsername, id), "A different billing item was found!");
	assertThrows(AuthenticationException.class, () -> BAP.getBillingItemById(failUsername, id),
		"An unauthorized user found a billing item by ID!");
    }

    @Test
    public void testGetOrganisationById() throws AuthenticationException {
	Long id = organisation1.getId();
	assertDoesNotThrow(() -> BAP.getOrganisationById(rootUsername, id),
		"Root couldn't find an organisation by ID!");
	assertEquals(organisation1, BAP.getOrganisationById(rootUsername, id), "A different organisation was found!");
	assertThrows(AuthenticationException.class, () -> BAP.getOrganisationById(failUsername, id),
		"An unauthorized user found an organisation by ID!");
    }

    //// Miscellaneous

    @Test
    public void testAddBillingItem() {
	assertDoesNotThrow(() -> BAP.addBillingItem(rootUsername, billingItem1), "Root couldn't add the billing item!");
	verify(billingItems).save(billingItem1);
	assertThrows(AuthenticationException.class, () -> BAP.addBillingItem(failUsername, billingItem2),
		"An unauthorized user added the billing item!");
	verify(billingItems, never()).save(billingItem2);
    }

    @Test
    public void testAddStatus() {
	assertDoesNotThrow(() -> BAP.addStatus(rootUsername, status1), "Root couldn't add the status!");
	verify(stati).save(status1);
	assertThrows(AuthenticationException.class, () -> BAP.addStatus(failUsername, status2),
		"An unauthorized user added the status!");
	verify(stati, never()).save(status2);
    }

    @Test
    public void testRemoveStatus() {
	Long id1 = status1.getId();
	assertDoesNotThrow(() -> BAP.removeStatus(rootUsername, id1), "Root couldn't remove the status!");
	verify(stati).deleteById(id1);
	Long id2 = status2.getId();
	assertThrows(AuthenticationException.class, () -> BAP.removeStatus(failUsername, id2),
		"An unauthorized user removed the status!");
	verify(stati, never()).deleteById(id2);
    }

    @Test
    public void testUpdateBillingItemStatus() {
	when(users.findByUsernameIgnoreCase(rootUsername)).thenReturn(new User()); // Don't return null
	assertDoesNotThrow(() -> BAP.updateBillingItemStatus(rootUsername, billingItem1.getInternID(), open),
		"Billing item's status couldn't be updated!");
	billingItem1.setStatusObj(open);
	verify(billingItems).save(billingItem1);
    }

    @Test
    public void testGetStatusById() {
	Long id = status1.getId();
	assertDoesNotThrow(() -> BAP.getStatusById(id), "Couldn't find a status by ID!");
	assertEquals(status1, BAP.getStatusById(id), "A different status was found!");
    }

    @Test
    public void testGetStatusByName() {
	String name = status1.getName();
	assertDoesNotThrow(() -> BAP.getStatusByName(name), "Couldn't find a status by name!");
	assertEquals(status1, BAP.getStatusByName(name), "A different status was found!");
    }
}
