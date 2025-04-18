package com.beratoztas.service;

import com.beratoztas.requests.CreateOrderRequest;
import com.beratoztas.requests.OrderStatusUpdateRequest;
import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.responses.OrderResponse;
import com.beratoztas.responses.PageResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IOrderService {

	public OrderResponse getOrderById(Long orderId,JwtUserDetails userDetails);
	
	public PageResponse<OrderResponse> getMyOrders(JwtUserDetails userDetails,RestPageRequest request);
	
	public PageResponse<OrderResponse> getAllOrders(RestPageRequest request);
	
	public OrderResponse createOrderFromCart(JwtUserDetails userDetails,CreateOrderRequest request);
	
	public OrderResponse updateOrderStatus(Long orderId,OrderStatusUpdateRequest newStatus);
	
}
