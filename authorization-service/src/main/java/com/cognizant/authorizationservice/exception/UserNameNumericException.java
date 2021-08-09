package com.cognizant.authorizationservice.exception;

/**
 * Class for handling UserNameNumericException
 */
public class UserNameNumericException extends RuntimeException {

	/**
	 * This method sets the custom error message
	 * 
	 * @param message
	 */
	public UserNameNumericException(String message) {
		super(message);
	}

}
