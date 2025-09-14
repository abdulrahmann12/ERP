package com.learn.erp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.learn.erp.events.UserAbsenceEvent;
import com.learn.erp.exception.MailSendingException;
import com.learn.erp.rabbitconfig.RabbitConstants;
import com.learn.erp.service.EmailService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserAbsenceConsumer {

	private final EmailService emailService;
	
	@RabbitListener(queues = RabbitConstants.ATTENDANCE_MARK_ABSENCES_QUEUE)
	public void handleUserAbsence(UserAbsenceEvent event) {
       System.out.println("📩 Received UserAbsenceEvent for user: " + event.getEmail());

       try {
			emailService.sendAbsenceAlert(event);
		} catch (Exception e) {
			throw new MailSendingException();
		}
	}
}
