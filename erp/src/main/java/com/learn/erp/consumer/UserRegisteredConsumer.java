package com.learn.erp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.learn.erp.events.UserRegisteredEvent;
import com.learn.erp.exception.MailSendingException;
import com.learn.erp.rabbitconfig.RabbitConstants;
import com.learn.erp.service.EmailService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserRegisteredConsumer {

	private final EmailService emailService;
	
	@RabbitListener(queues = RabbitConstants.USER_REGISTERED_QUEUE)
	public void handleUserRegistered(UserRegisteredEvent event) {
       System.out.println("📩 Received UserRegisteredEvent for user: " + event.getEmail());

       try {
			emailService.sendWelcomeEmail(event);
		} catch (Exception e) {
			throw new MailSendingException();
		}
	}
}
