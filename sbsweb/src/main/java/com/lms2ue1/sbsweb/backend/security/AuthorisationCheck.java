package com.lms2ue1.sbsweb.backend.security;

/**
 * This class provides predicates to say, whether a user is allowed to do something.
 */
public class AuthorisationCheck {
	/**
	 * This class is a singleton.
	 */
	private static AuthorisationCheck instance;
	
	private AuthorisationCheck() {
	}
	
	/**
	 * Method to get the instance.
	 * @return the instance
	 */
	public static AuthorisationCheck getAuthInstance() {
		if (instance == null) {
			synchronized (AuthorisationCheck.class) {
				if (instance == null) {
					instance = new AuthorisationCheck();
				}
			}
		}
		return instance;
	}
	
	/**
	 * Is the given user allowed to do anything with the organisation?
	 * 
	 * @param username = the user, who wants to work with it.
	 * @param oID = the organisation in question.
	 * @return true = yes, he*she is. false = no, he*she isn't.
	 */
	public boolean checkOrganisation(String username, long oID) {
		// TODO: Implement me!
		return false;
	}

}
