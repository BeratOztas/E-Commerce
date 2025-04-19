package com.beratoztas.controller;

import com.beratoztas.dto.request.CreateOrderRequest;
import com.beratoztas.dto.request.OrderStatusUpdateRequest;
import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.response.OrderResponse;
import com.beratoztas.dto.response.PageResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IRestOrderController {

	public ApiResponse<OrderResponse> getOrderById(Long orderId, JwtUserDetails userDetails);

	public ApiResponse<PageResponse<OrderResponse>> getMyOrders(JwtUserDetails userDetails, RestPageRequest request);

	public ApiResponse<PageResponse<OrderResponse>> getAllOrders(RestPageRequest request);

	public ApiResponse<OrderResponse> createOrderFromCart(JwtUserDetails userDetails, CreateOrderRequest request);

	public ApiResponse<OrderResponse> updateOrderStatus(Long orderId, OrderStatusUpdateRequest newStatus);

}
