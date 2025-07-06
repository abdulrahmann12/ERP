package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class InvalidResetCodeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public InvalidResetCodeException() {
        super(Messages.INVALID_RESET_CODE);
    }
}