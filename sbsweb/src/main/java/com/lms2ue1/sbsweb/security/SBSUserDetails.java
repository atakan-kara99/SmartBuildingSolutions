package com.lms2ue1.sbsweb.security;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.lms2ue1.sbsweb.backend.model.User;

/**
 * Provides the user credentials from the database.
 * 
 * @author nataliekaufhold
 *
 */
public class SBSUserDetails implements UserDetails {
	
	/**
	 * ID for serial object.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instance of 'User' to use to authenticate.
	 */
	private final User user;
	
	public SBSUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
