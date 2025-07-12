package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class CustomerNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException() {
		super(Messages.CUSTOMER_NOT_FOUND);
	}
}