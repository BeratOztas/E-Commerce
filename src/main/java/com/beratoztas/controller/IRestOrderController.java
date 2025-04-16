package com.beratoztas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.beratoztas.requests.CreateOrderRequest;
import com.beratoztas.requests.OrderStatusUpdateRequest;
import com.beratoztas.responses.OrderResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IRestOrderController {

	public ResponseEntity<OrderResponse> getOrderById(Long orderId, JwtUserDetails userDetails);

	public ResponseEntity<List<OrderResponse>> getMyOrders(JwtUserDetails userDetails);

	public ResponseEntity<List<OrderResponse>> getAllOrders();

	public ResponseEntity<OrderResponse> createOrderFromCart(JwtUserDetails userDetails, CreateOrderRequest request);

	public ResponseEntity<OrderResponse> updateOrderStatus(Long orderId, OrderStatusUpdateRequest newStatus);

}
