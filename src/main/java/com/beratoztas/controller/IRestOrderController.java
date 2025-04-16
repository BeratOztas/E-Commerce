package com.beratoztas.controller;

import java.util.List;

import com.beratoztas.requests.CreateOrderRequest;
import com.beratoztas.requests.OrderStatusUpdateRequest;
import com.beratoztas.responses.OrderResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IRestOrderController {

	public ApiResponse<OrderResponse> getOrderById(Long orderId, JwtUserDetails userDetails);

	public ApiResponse<List<OrderResponse>> getMyOrders(JwtUserDetails userDetails);

	public ApiResponse<List<OrderResponse>> getAllOrders();

	public ApiResponse<OrderResponse> createOrderFromCart(JwtUserDetails userDetails, CreateOrderRequest request);

	public ApiResponse<OrderResponse> updateOrderStatus(Long orderId, OrderStatusUpdateRequest newStatus);

}
