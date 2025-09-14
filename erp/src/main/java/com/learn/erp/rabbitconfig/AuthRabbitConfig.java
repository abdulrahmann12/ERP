package com.learn.erp.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.learn.erp.rabbitconfig.RabbitConstants.*;

@Configuration
public class AuthRabbitConfig {

	@Bean
	public TopicExchange authExchange() {
	    return new TopicExchange(AUTH_EXCHANGE);
	}

	@Bean
	public Queue userRegisteredQueue() {
	    return new Queue(USER_REGISTERED_QUEUE, true);
	}

	@Bean
	public Queue passwordResetQueue() {
	    return new Queue(PASSWORD_RESET_QUEUE, true);
	}
	
	@Bean
	public Queue RegenerateCodeQueue() {
	    return new Queue(PASSWORD_CODE_REGENERATED_QUEUE, true);
	}

	@Bean
	public Binding userRegisteredBinding(TopicExchange authExchange, Queue userRegisteredQueue) {
	    return BindingBuilder.bind(userRegisteredQueue).to(authExchange).with(USER_REGISTERED_KEY);
	}

	@Bean
	public Binding passwordResetBinding(TopicExchange authExchange, Queue passwordResetQueue) {
	    return BindingBuilder.bind(passwordResetQueue).to(authExchange).with(PASSWORD_RESET_KEY);
	}

	@Bean
	public Binding RegenerateCodeBinding(TopicExchange authExchange, Queue RegenerateCodeQueue) {
	    return BindingBuilder.bind(RegenerateCodeQueue).to(authExchange).with(PASSWORD_CODE_REGENERATED_KEY);
	}
}
