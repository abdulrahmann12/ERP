package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class DepartmentNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DepartmentNotFoundException() {
		super(Messages.DEPARTMENT_NOT_FOUND);
	}
}