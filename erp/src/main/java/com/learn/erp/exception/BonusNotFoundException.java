package com.learn.erp.exception;

import com.learn.erp.config.Messages;

public class BonusNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BonusNotFoundException() {
		super(Messages.BONUS_NOT_FOUND);
	}
}