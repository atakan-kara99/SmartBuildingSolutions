package com.lms2ue1.sbsweb.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lms2ue1.sbsweb.backend.model.*;
import com.lms2ue1.sbsweb.backend.repository.UserRepository;
import com.lms2ue1.sbsweb.security.SBSUserDetails;

//import org.springframework.security.core.userdetails.User;

/**
 * Service to check the user data in the database, whether the credentials are allright.
 * 
 * @author nataliekaufhold
 *
 */
@Service
public class DBUserDetailsService implements UserDetailsService {

	/**
	 * Repository to use.
	 */
	private UserRepository userRepo;
	
	public DBUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User '%s' not found.", username));
		}
		return new SBSUserDetails(user);
	}
	
}