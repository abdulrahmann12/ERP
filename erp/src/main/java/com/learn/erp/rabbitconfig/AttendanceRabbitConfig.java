package com.learn.erp.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.learn.erp.rabbitconfig.RabbitConstants.*;

@Configuration
public class AttendanceRabbitConfig {

	@Bean
	public TopicExchange attendanceExchange() {
	    return new TopicExchange(ATTENDANCE_EXCHANGE);
	}

	@Bean
	public Queue markAbsencesAndNotify() {
	    return new Queue(ATTENDANCE_MARK_ABSENCES_QUEUE, true);
	}

	@Bean
	public Binding markAbsencesAndNotifyBinding(TopicExchange attendanceExchange, Queue markAbsencesAndNotify) {
	    return BindingBuilder.bind(markAbsencesAndNotify).to(attendanceExchange).with(ATTENDANCE_MARK_ABSENCES_KEY);
	}
}
