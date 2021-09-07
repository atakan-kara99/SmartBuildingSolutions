package com.lms2ue1.sbsweb.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SBSUserDetailsService implements UserDetailsService {

	@Value("${credentials.default.username}")
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
	}
}