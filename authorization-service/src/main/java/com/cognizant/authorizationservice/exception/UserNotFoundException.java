package com.cognizant.authorizationservice.exception;

/**
 * Class for handling UserNotFoundException
 */
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String msg) {
		super(msg);
	}
}
