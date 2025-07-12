package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class PayrollNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public PayrollNotFoundException() {
		super(Messages.PAYROLL_NOT_FOUND);
	}
}