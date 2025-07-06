package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class InvalidCurrentPasswordException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public InvalidCurrentPasswordException() {
        super(Messages.INVALID_PASSWORD);
    }
}