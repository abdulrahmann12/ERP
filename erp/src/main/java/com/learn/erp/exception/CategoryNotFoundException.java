package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class CategoryNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException() {
		super(Messages.CATEGORY_NOT_FOUND);
	}
	
}