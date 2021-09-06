package com.lms2ue1.sbsweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms2ue1.sbsweb.model.*;
import com.lms2ue1.sbsweb.repository.UserRepository;

@Service
public class DBUserDetailsService implements UserDetailsService {

	/*@Value("${credentials.default.username}")
	private String username;

	@Value("${credentials.default.password}")
	private String password;

	@Autowired
	PasswordEncoder passwordEncoder;
	

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		if (this.username.compareTo(username) != 0) {
			throw new UsernameNotFoundException(String.format("User '%s' not found.", username));
		}
		return User.withUsername(this.username).password(passwordEncoder.encode(password))
			.roles("ADMIN").build();
	}*/
	
	private final UserRepository userRepo;
	
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