package com.lms2ue1.sbsweb.backend.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	billingItems.deleteAll();
	billingUnits.deleteAll();
	contracts.deleteAll();
	projects.deleteAll();
	addresses.deleteAll();
	users.deleteAll();
	roles.deleteAll();
	organisations.deleteAll();
    }

    // Organisation

    @Test
    public void testAddOrganisation() {
	String username = "root";
	Organisation organisation = new Organisation("Fritz M�ller GmbH");
	BAP.addOrganisation(username, organisation);
	assertDoesNotThrow(() -> organisations.findById(organisation.getId()).get(), "Organisation couldn't be added!");
	assertEquals(organisations.findById(organisation.getId()).get(), organisation,
		"Another organisation with same id is present!");
    }

    @Test
    public void testRemoveOrganisation() {
	// Add
	String username = "root";
	Organisation organisation = new Organisation("Fritz M�ller GmbH");
	BAP.addOrganisation(username, organisation);
	assertDoesNotThrow(() -> organisations.findById(organisation.getId()).get(), "Organisation couldn't be added!");
	// Remove
	BAP.removeOrganisation(username, organisation.getId());
	assertTrue(organisations.findById(organisation.getId()).isEmpty(), "Organisation wasn't removed!");
    }

    @Test
    public void testUpdateOrganisation() {
	// Add
	String username = "root";
	Organisation organisation = new Organisation("Fritz M�ller GmbH");
	BAP.addOrganisation(username, organisation);
	assertDoesNotThrow(() -> organisations.findById(organisation.getId()).get(), "Organisation couldn't be added!");
	// Update
	Organisation newOrganisation = new Organisation("Fritz M�ller-Schulz GmbH");
	BAP.updateOrganisation(username, organisation.getId(), newOrganisation);
	assertThrows(NoSuchElementException.class, () -> organisations.findById(organisation.getId()),
		"Old organisation wasn't removed");
    }
}
