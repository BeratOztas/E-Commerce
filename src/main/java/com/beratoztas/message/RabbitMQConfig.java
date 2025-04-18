package com.beratoztas.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String ORDER_QUEUE = "order.created.queue";
	public static final String ORDER_EXCHANGE = "order.exchange";
	public static final String ORDER_ROUTING_KEY = "order.routing.key";
	
	public static final String ORDER_STATUS_QUEUE ="order.status.queue";
	public static final String ORDER_STATUS_ROUTING_KEY ="order.status.changed";
 	

	@Bean
	public Queue orderQueue() {
		return new Queue(ORDER_QUEUE, false);
	}
	
	@Bean
	public Queue orderStatusQueue() {
		return new Queue(ORDER_STATUS_QUEUE,false);
	}
	
	@Bean
	public DirectExchange orderExchange() {
		return new DirectExchange(ORDER_EXCHANGE);
	}

	@Bean
	public Binding orderBinding(Queue orderQueue, DirectExchange orderExchange) {
		return BindingBuilder.bind(orderQueue).to(orderExchange).with(ORDER_ROUTING_KEY);
	}
	
	@Bean
	public Binding orderStatusBinding() {
		return BindingBuilder.bind(orderStatusQueue())
				.to(orderExchange())
				.with(ORDER_STATUS_ROUTING_KEY);
	}
	
	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
	    return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
	    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	    rabbitTemplate.setMessageConverter(messageConverter());
	    return rabbitTemplate;
	}
}
