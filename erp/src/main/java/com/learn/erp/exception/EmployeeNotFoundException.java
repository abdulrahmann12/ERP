package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class EmployeeNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException() {
		super(Messages.EMPLOYEE_NOT_FOUND);
	}
}