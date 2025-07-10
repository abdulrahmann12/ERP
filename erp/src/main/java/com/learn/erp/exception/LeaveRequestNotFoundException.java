package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class LeaveRequestNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public LeaveRequestNotFoundException() {
		super(Messages.REQUEST_NOT_FOUND);
	}
}