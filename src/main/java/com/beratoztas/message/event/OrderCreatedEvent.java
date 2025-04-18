package com.beratoztas.message.event;

import java.io.Serializable;
import java.math.BigDecimal;

import com.beratoztas.responses.OrderResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long orderId;
	private Long userId;
	private String email;
	private BigDecimal totalPrice;

	public OrderCreatedEvent(OrderResponse orderResponse) {
		this.orderId = orderResponse.getId();
	    this.totalPrice = orderResponse.getTotalPrice();
	    this.userId = orderResponse.getUser().getId(); 
	    this.email = orderResponse.getUser().getEmail(); 
	}
}
