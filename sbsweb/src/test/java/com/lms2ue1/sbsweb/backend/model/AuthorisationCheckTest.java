package com.lms2ue1.sbsweb.backend.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lms2ue1.sbsweb.backend.repository.*;
import com.lms2ue1.sbsweb.backend.security.AuthorisationCheck;

@SpringBootTest
class AuthorisationCheckTest {

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@MockBean
	private RoleRepository roleMock;
	@MockBean
	private OrganisationRepository orgMock;
	@MockBean
	private UserRepository userMock;

	@InjectMocks
	private AuthorisationCheck authCheck;
	private AutoCloseable closeable;

	@BeforeEach
	public void init() {
		closeable = MockitoAnnotations.openMocks(this);

		/*Organisation org0 = new Organisation("SBS");
		Organisation org1 = new Organisation("Tiefbau");

		Role role0 = new Role("SysAdmin", null, null, null, org0);
		Role role1 = new Role("OrgAdmin", null, null, null, org1);

		User user0 = new User("Peter", "Müller", role0, "root", passwordEncoder.encode("admin"));*/
	}
	
	@AfterEach
	private void close() throws Exception {
		closeable.close();
	}

	// ------------------ Getter-Tests --------------//
	@Test
	void getUserOverRole() {
		// Can we find the user in the user List from the role entity?
		//assertTrue(roleMock.findByName("SysAdmin").getUsers().contains(userMock.findByUsername("root")));
		//when(roleMock.findByName("SysAdmin").getUsers()).thenReturn(userMock.findByUsername("root"));
		//assertTrue(true);
	}

}
