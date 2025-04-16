package com.beratoztas.service;

import java.util.List;

import com.beratoztas.enums.OrderStatus;
import com.beratoztas.requests.CreateOrderRequest;
import com.beratoztas.requests.OrderStatusUpdateRequest;
import com.beratoztas.responses.OrderResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IOrderService {

	public OrderResponse getOrderById(Long orderId,JwtUserDetails userDetails);
	
	public List<OrderResponse> getMyOrders(JwtUserDetails userDetails);
	
	public List<OrderResponse> getAllOrders();
	
	public OrderResponse createOrderFromCart(JwtUserDetails userDetails,CreateOrderRequest request);
	
	public OrderResponse updateOrderStatus(Long orderId,OrderStatusUpdateRequest newStatus);
	
}
