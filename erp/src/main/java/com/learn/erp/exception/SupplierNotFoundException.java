package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class SupplierNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SupplierNotFoundException() {
		super(Messages.SUPPLIER_NOT_FOUND);
	}
}