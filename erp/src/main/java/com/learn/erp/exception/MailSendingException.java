package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class MailSendingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public MailSendingException() {
        super(Messages.FAILED_EMAIL);
    }
}