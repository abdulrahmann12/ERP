package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class UsernameAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UsernameAlreadyExistsException() {
		super(Messages.USERNAME_ALREADY_EXISTS);
	}
	
}