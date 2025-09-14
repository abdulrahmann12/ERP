package com.learn.erp.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.learn.erp.rabbitconfig.RabbitConstants.*;

@Configuration
public class EmployeeRabbitConfig {

	@Bean
	public TopicExchange employeeExchange() {
	    return new TopicExchange(EMPLOYEE_EXCHANGE);
	}

	@Bean
	public Queue addNewEmployeeQueue() {
	    return new Queue(ADD_NEW_EMPLOYEE_QUEUE, true);
	}

	@Bean
	public Binding addNewEmployee(TopicExchange employeeExchange, Queue addNewEmployeeQueue) {
	    return BindingBuilder.bind(addNewEmployeeQueue).to(employeeExchange).with(NEW_EMPLOYEE_KEY);
	}
}
