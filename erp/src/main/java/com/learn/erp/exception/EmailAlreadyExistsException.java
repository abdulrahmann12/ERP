package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class EmailAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException() {
		super(Messages.EMAIL_ALREADY_EXISTS);
	}
	
}