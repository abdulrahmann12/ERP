package com.learn.erp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.learn.erp.events.AddEmployeeEvent;
import com.learn.erp.exception.MailSendingException;
import com.learn.erp.rabbitconfig.RabbitConstants;
import com.learn.erp.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddEmployeeConsumer {

	private final EmailService emailService;

	@RabbitListener(queues = RabbitConstants.ADD_NEW_EMPLOYEE_QUEUE)
	public void handleNewEmployee(AddEmployeeEvent event) {
		System.out.println("📩 Received AddEmployeeEvent for user: " + event.getEmail());

		try {
			emailService.sendEmployeeWelcomeEmail(event);
		} catch (Exception e) {
			throw new MailSendingException();
		}
	}
}
