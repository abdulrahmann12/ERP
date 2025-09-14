package com.learn.erp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.learn.erp.config.Messages;
import com.learn.erp.events.PasswordResetRequestedEvent;
import com.learn.erp.exception.MailSendingException;
import com.learn.erp.rabbitconfig.RabbitConstants;
import com.learn.erp.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetConsumer {
	private final EmailService emailService;

	@RabbitListener(queues = RabbitConstants.PASSWORD_RESET_QUEUE)
	public void handlePasswordResetRequested(PasswordResetRequestedEvent event) {
		System.out.println("📩 Received PasswordResetRequestedEvent for user: " + event.getEmail());

		try {
			emailService.sendCode(event, Messages.RESET_PASSWORD);
		} catch (Exception e) {
			throw new MailSendingException();
		}
	}
}
