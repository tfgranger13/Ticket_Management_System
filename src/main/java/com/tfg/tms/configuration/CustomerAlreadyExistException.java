package com.tfg.tms.configuration;

/*
 * This class is for exceptions thrown during the registration process to let the visitor know that an account with that email already exists
 */

public class CustomerAlreadyExistException extends Exception {
	private static final long serialVersionUID = 1L;

	public CustomerAlreadyExistException() {
		super();
	}

	public CustomerAlreadyExistException(String message) {
		super(message);
	}

	public CustomerAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
