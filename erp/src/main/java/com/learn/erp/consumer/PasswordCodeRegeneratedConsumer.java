package com.learn.erp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.learn.erp.config.Messages;
import com.learn.erp.events.PasswordCodeRegeneratedEvent;
import com.learn.erp.exception.MailSendingException;
import com.learn.erp.rabbitconfig.RabbitConstants;
import com.learn.erp.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordCodeRegeneratedConsumer {

	private final EmailService emailService;

	@RabbitListener(queues = RabbitConstants.PASSWORD_CODE_REGENERATED_QUEUE)
	public void handlePasswordCodeRegenerated(PasswordCodeRegeneratedEvent event) {
		System.out.println("📩 Received PasswordCodeRegeneratedEvent for user: " + event.getEmail());

		try {
			emailService.sendRegenerateCode(event, Messages.RESEND_CODE);
		} catch (Exception e) {
			throw new MailSendingException();
		}
	}
}
