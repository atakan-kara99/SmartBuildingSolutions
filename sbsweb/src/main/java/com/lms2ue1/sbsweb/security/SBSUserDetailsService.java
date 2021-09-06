package com.lms2ue1.sbsweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms2ue1.sbsweb.repository.UserRepository;

@Service
public class SBSUserDetailsService implements UserDetailsService {

	/*@Value("${credentials.default.username}")
	private String username;

	@Value("${credentials.default.password}")
	private String password;

	@Autowired
	PasswordEncoder passwordEncoder;*/
	
	private final UserRepository userRepo;
	
	public SBSUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.lms2ue1.sbsweb.model.User user = userRepo.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User '%s' not found.", username));
		}
		return null;
	}

	/*@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		if (this.username.compareTo(username) != 0) {
			throw new UsernameNotFoundException(String.format("User '%s' not found.", username));
		}
		return User.withUsername(this.username).password(passwordEncoder.encode(password))
			.roles("ADMIN").build();
	}*/
	
	
}