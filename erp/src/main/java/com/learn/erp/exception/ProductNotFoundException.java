package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class ProductNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException() {
		super(Messages.PRODUCT_NOT_FOUND);
	}
	
}