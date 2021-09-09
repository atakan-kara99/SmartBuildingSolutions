package com.lms2ue1.sbsweb.backend.security;

/**
 * This class provides predicates to say, whether a user is allowed to do
 * something.
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
	 * 
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

	// ---------- Organisation and stuff ------------- //

	/**
	 * Is the given user allowed to do anything with the organisation?
	 * 
	 * @param username = the user, who wants to work with it.
	 * @param oID      = the organisation in question.
	 * @return true = yes, he*she is. false = no, he*she isn't.
	 */
	public boolean checkOrganisation(String username, long oID) {
		// TODO: Implement me!
		return false;
	}

	/**
	 * Is the given user allowed to do anything with the project?
	 * 
	 * @param username = the user, who wants to work with it.
	 * @param pID      = the project in question.
	 * @return true = yes, he*she is. false = no, he*she isn't.
	 */
	public boolean checkProject(String username, long pID) {
		// TODO: Implement me!
		return false;
	}

	/**
	 * Is the given user allowed to do anything with the contract?
	 * 
	 * @param username = the user, who wants to work with it.
	 * @param cID      = the contract in question.
	 * @return true = yes, he*she is. false = no, he*she isn't.
	 */
	public boolean checkContract(String username, long cID) {
		// TODO: Implement me!
		return false;
	}

	/**
	 * Is the given user allowed to do anything with the billing unit?
	 * 
	 * @param username = the user, who wants to work with it.
	 * @param buID      = the billing unit in question.
	 * @return true = yes, he*she is. false = no, he*she isn't.
	 */
	public boolean checkBillingUnit(String username, long buID) {
		// TODO: Implement me!
		return false;
	}

	/**
	 * Is the given user allowed to do anything with the billing item?
	 * 
	 * @param username = the user, who wants to work with it.
	 * @param bID      = the billing item in question.
	 * @return true = yes, he*she is. false = no, he*she isn't.
	 */
	public boolean checkBillingItem(String username, long bID) {
		// TODO: Implement me!
		return false;
	}

	// ---------- User management ------------- //

	/**
	 * Is the given user allowed to do anything with the other user?
	 * 
	 * @param username = the user, who wants to work with it.
	 * @param uID      = the other user to manage.
	 * @return true = yes, he*she is. false = no, he*she isn't.
	 */
	public boolean checkUser(String username, long uID) {
		// TODO: Implement me!
		return false;
	}
	// TODO: The status => Different issue.

}
