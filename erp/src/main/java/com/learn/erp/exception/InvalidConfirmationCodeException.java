package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class InvalidConfirmationCodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidConfirmationCodeException() {
        super(Messages.INVALID_CONFIRM_EMAIL);
    }
}