package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class PurchaseNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public PurchaseNotFoundException() {
		super(Messages.PURCHASE_NOT_FOUND);
	}
	
}