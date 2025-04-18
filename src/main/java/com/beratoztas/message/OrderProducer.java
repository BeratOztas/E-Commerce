package com.beratoztas.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.beratoztas.message.config.RabbitMQConfig;
import com.beratoztas.message.event.OrderCreatedEvent;
import com.beratoztas.message.event.OrderStatusChangedEvent;

@Component
public class OrderProducer {

	private RabbitTemplate rabbitTemplate;
	
	public OrderProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ORDER_ROUTING_KEY, orderCreatedEvent);
	}
	
	public void sendOrderStatusChangedEvent(OrderStatusChangedEvent event) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ORDER_STATUS_ROUTING_KEY, event);
	}
	
}
