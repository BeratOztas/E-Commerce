package com.beratoztas.message.event;

import com.beratoztas.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChangedEvent {

	private Long orderId;
	private Long userId;
	private String email;
	private OrderStatus oldStatus;
	private OrderStatus newStatus;

}
