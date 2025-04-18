package com.beratoztas.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.beratoztas.mail.IMailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderEventListener {

	private IMailService mailService;
	
	public OrderEventListener(IMailService mailService) {
		this.mailService = mailService;
	}

	@RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
	public void handleOrderCreatedEvent(OrderCreatedEvent event) {
		 log.info("📥 [RabbitMQ] Received OrderCreatedEvent: {}", event);
		 
		 mailService.sendOrderConfirmationEmail(event.getEmail(), event.getOrderId(), event.getTotalPrice());
	}
	
	@RabbitListener(queues = RabbitMQConfig.ORDER_STATUS_QUEUE)
	public void handleOrderStatusChangedEvent(OrderStatusChangedEvent event) {
		 log.info("📥 [RabbitMQ] Received OrderStatusChangedEvent: {}", event);
		 
		 String body =String.format("Dear Customer,\n"
		 		+ "Your order #%d has been updated from [%s] to [%s].",
				 event.getOrderId()
				 ,event.getOldStatus().name()
				 ,event.getNewStatus().name()
			);
		 
		 mailService.sendEmail(event.getEmail(), "Order Status Updated", body);
	}
	
}
