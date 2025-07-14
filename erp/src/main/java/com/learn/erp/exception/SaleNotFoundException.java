package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class SaleNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SaleNotFoundException() {
		super(Messages.SALE_NOT_FOUND);
	}
	
}