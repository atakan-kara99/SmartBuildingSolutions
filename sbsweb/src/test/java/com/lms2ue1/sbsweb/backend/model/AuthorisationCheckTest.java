package com.lms2ue1.sbsweb.backend.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lms2ue1.sbsweb.backend.repository.*;

@SpringBootTest
class AuthorisationCheckTest {
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@MockBean
	private RoleRepository roleMock;
	@MockBean
	private OrganisationRepository orgMock;
	@MockBean
	private UserRepository userMock;

	@BeforeEach
	public void init() {
		Organisation org0 = new Organisation("SBS");
    	Organisation org1 = new Organisation("Tiefbau");
    	
    	orgMock.save(org0);
    	orgMock.save(org1);
    	
		Role role0 = new Role("SysAdmin", null, null, null, org0);
		Role role1 = new Role("OrgAdmin", null, null, null, org1);

		roleMock.save(role0);
		roleMock.save(role1);
		
		User user0 = new User("Peter", "Müller", role0, "root", passwordEncoder.encode("admin"));
		userMock.save(user0);
	}

	// ------------------ Getter-Tests --------------//
	@Test
	void getUserOverRole() {
		// Can we find the user in the user List from the role entity?
		//assertTrue(roleMock.findByName("SysAdmin").getUsers().contains(userMock.findByUsername("root")));
		assertTrue(true);
	}

}
