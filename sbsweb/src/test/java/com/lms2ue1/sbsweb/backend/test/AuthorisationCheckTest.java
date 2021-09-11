package com.lms2ue1.sbsweb.backend.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private Organisation org0;
    private Organisation org1;
    private Role role0;
    private Role role1;
    private User user0;

    @BeforeEach
    public void init() {
	closeable = MockitoAnnotations.openMocks(this);

	org0 = new Organisation("SBS");
	org1 = new Organisation("Tiefbau");

	role0 = new Role("SysAdmin", null, null, null, org0, true);
	role1 = new Role("OrgAdmin", null, null, null, org1, true);

	user0 = new User("Peter", "Müller", role0, "root", passwordEncoder.encode("admin"));
    }

    @AfterEach
    public void close() throws Exception {
	closeable.close();
    }
    
    /// Role tests
    
    @Test
    @DisplayName("Role of root == SysAdmin")
    public void testGetRole() {
	when(userMock.findByUsername("root")).thenReturn(user0);
	assertTrue(authCheck.getRole("root").equals(role0));
    }

}