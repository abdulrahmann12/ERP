package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super(Messages.USER_NOT_FOUND);
	}
}