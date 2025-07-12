package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class PayrollAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PayrollAlreadyExistsException(String userFullName, int month, int year) {
        super(Messages.PAYROLL_ALREADY_EXISTS + userFullName + " in " + month + "/" + year);
    }
}
