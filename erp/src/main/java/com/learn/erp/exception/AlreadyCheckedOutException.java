package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class AlreadyCheckedOutException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AlreadyCheckedOutException() {
		super(Messages.USER_ALREADY_CHECK_OUT);
	}
}