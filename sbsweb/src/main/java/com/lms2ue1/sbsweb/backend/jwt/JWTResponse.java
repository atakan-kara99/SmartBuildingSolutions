package com.lms2ue1.sbsweb.backend.jwt;

import java.io.Serializable;

/**
 * Used in jwt responses for easy access to important variables
 * @author Luca Anthony Schwarz (sunfl0w)
 */
public class JWTResponse implements Serializable {
	private final String jwt;

	public JWTResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getToken() {
		return this.jwt;
	}
}
