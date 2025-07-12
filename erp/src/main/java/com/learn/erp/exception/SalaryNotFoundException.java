package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class SalaryNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SalaryNotFoundException() {
		super(Messages.SALARY_NOT_FOUND);
	}
}