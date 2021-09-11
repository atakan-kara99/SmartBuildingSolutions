package com.lms2ue1.sbsweb.backend.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.repository.*;
import com.lms2ue1.sbsweb.backend.security.AuthorisationCheck;

@SpringBootTest
class AuthorisationCheckTest {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // --------- Repositorie mocks --------------------//
    // !!! Every repo needs to be mocked! EVERY REPO! !!!
    @MockBean
    private RoleRepository roleMock;
    @MockBean
    private OrganisationRepository orgMock;
    @MockBean
    private UserRepository userMock;
    @MockBean
    private ProjectRepository proMock;
    @MockBean
    private ContractRepository conMock;
    @MockBean
    private BillingItemRepository billItemMock;
    @MockBean
    private BillingUnitRepository billUnitMock;
    @MockBean
    private AddressRepository addMock;

    @InjectMocks
    private AuthorisationCheck authCheck;
    private AutoCloseable closeable;

    // Invars
    private Address address0;
    private Address address1;
    private Project project0;
    private Project project1;
    private Contract contract0;
    private Contract contract1;
    private BillingUnit billingUnit0;
    private BillingUnit billingUnit1;
    private BillingItem billingItem0;
    private BillingItem billingItem1;
    private Organisation org0;
    private Organisation org1;
    private Role role0;
    private Role role1;
    private User user0;
    private User user1;

    @BeforeEach
    public void init() {
	closeable = MockitoAnnotations.openMocks(this);

	org0 = new Organisation("SBS");
	org1 = new Organisation("Tiefbau");
	address0 = new Address("Main Street", 1337, 14, "NY City", "Deutschland");
	address1 = new Address("2nd Street", 42, 15, "GBR City", "France");
	project0 = new Project("Burj Khalifa2", "steht direkt daneben", "2010-02-07", "2021-06-01", Status.OK,
		234578900, "Die den anderen Turm auch gemacht haben", null, null, null, address0, org0);
	project1 = new Project("Berliner Flughafen xD", "Morgen ist es soweit", "2010-12-28", "2015-01-01", Status.OPEN,
		1300500000, "Nicht die vom Burj Khalifa", null, null, null, address1, org1);
	contract0 = new Contract("Baby beruhigen", "Es schreit.", Status.NO_STATUS, "Nanny", "Mutter", List.of(),
		project0);
	contract1 = new Contract("Kosten klein halten", "Teuer", null, null, null, List.of(org1), project1);
	billingUnit0 = new BillingUnit("sd", null, "kg", "1973", "2001", 2.1, 7, contract0);
	billingUnit1 = new BillingUnit("sssdad", null, "�", "15999", "2201", 1, 0, contract1);
	billingItem1 = new BillingItem("Fenster einbauen", 0, null, null, 99, null, 0, null, null, billingUnit1, null);
	billingItem0 = new BillingItem("Heizung montieren", 2, "Heizko B7-2 fensternah einbauen.", Status.OPEN, 0, null,
		7, null, null, billingUnit0, List.of(billingItem1));
	role0 = new Role("SysAdmin", List.of(project0, project1), List.of(contract0, contract1),
		List.of(billingItem0, billingItem1), org0, true);
	role1 = new Role("OrgAdmin", null, null, null, org1, true);
	user0 = new User("Peter", "Müller", role0, "root", passwordEncoder.encode("admin"));
	user1 = new User("Hans", "Frans", role1, "orgadmin", passwordEncoder.encode("org"));

	when(userMock.findByUsername(user0.getUsername())).thenReturn(user0);
    }

    @AfterEach
    public void close() throws Exception {
	closeable.close();
    }

    /// ---------------------- Role tests

    @Test
    @DisplayName("testGetRole")
    public void testGetRole() {
	when(roleMock.findById(role0.getId())).thenReturn(Optional.of(role0));
	assertTrue(authCheck.getRole(user0.getUsername()).equals(role0));
    }

    // ----------------------- Role Join tests
    // TODO: Test user without permission.

    // !! Inverse join ist nicht testbar !!
    /*
     * @Test
     * 
     * @DisplayName("testCheckAddress") public void testCheckAddress() {
     * when(addMock.findById(address0.getId())).thenReturn(Optional.of(address0));
     * when(proMock.findById(project0.getId())).thenReturn(Optional.of(project0));
     * System.out.println(address0.getProject());
     * assertTrue(authCheck.checkAddress("root", address0.getId())); }
     */

    @Test
    @DisplayName("testCheckOrganisation")
    public void testCheckOrganisation() {
	when(orgMock.findById(org0.getId())).thenReturn(Optional.of(org0));
	assertTrue(authCheck.checkOrganisation(user0.getUsername(), org0.getId()));
    }

    @Test
    @DisplayName("testCheckProject")
    public void testCheckProject() {
	when(proMock.findById(project0.getId())).thenReturn(Optional.of(project0));
	assertTrue(authCheck.checkProject(user0.getUsername(), project0.getId()));
    }

    @Test
    @DisplayName("testCheckContract")
    public void testCheckContract() {
	when(conMock.findById(contract0.getId())).thenReturn(Optional.of(contract0));
	assertTrue(authCheck.checkContract(user0.getUsername(), contract0.getId()));
    }

    @Test
    @DisplayName("testCheckBillingUnit")
    public void testCheckBillingUnit() {
	when(billUnitMock.findById(billingUnit0.getId())).thenReturn(Optional.of(billingUnit0));
	assertTrue(authCheck.checkBillingUnit(user0.getUsername(), billingUnit0.getId()));
    }

    @Test
    @DisplayName("testCheckRootBillingItem")
    public void testCheckRootBillingItem() {
	when(billItemMock.findById(billingItem0.getId())).thenReturn(Optional.of(billingItem0));
	assertTrue(authCheck.checkBillingItem(user0.getUsername(), billingItem0.getId()));
    }

    @Test
    @DisplayName("testCheckLeafBillingItem")
    public void testCheckLeafBillingItem() {
	when(billItemMock.findById(1l)).thenReturn(Optional.of(billingItem0));
	when(billItemMock.findById(0l)).thenReturn(Optional.of(billingItem1));
	assertTrue(authCheck.checkBillingItem(user0.getUsername(), 0l));
    }

    // TODO: Tests für die User Checks
    // --------------- User Check Tests

    @Test
    public void testIsSysAdmin() {
	assertTrue(authCheck.isSysAdmin(user0.getUsername()));
    }

    @Test
    public void testGetOrgAdminTrue() {
	when(userMock.findByUsername(user1.getUsername())).thenReturn(user1);
	assertTrue(authCheck.getOrgAdminID(user1.getUsername()) == user1.getId());
    }

    @Test
    public void testManageUserSysAdmin() {
	when(userMock.findById(user1.getId())).thenReturn(Optional.of(user1));
	assertTrue(authCheck.canManageUser(user0.getUsername(), user1.getId()));
    }

    // TODO: Tests für den Status => Eigener Issue

}