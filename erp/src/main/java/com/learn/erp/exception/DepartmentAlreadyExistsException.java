package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class DepartmentAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DepartmentAlreadyExistsException() {
		super(Messages.DEPARTMENT_ALREADY_EXISTS);
	}
	
}