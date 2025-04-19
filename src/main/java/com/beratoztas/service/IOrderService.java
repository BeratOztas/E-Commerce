package com.beratoztas.service;

import com.beratoztas.dto.request.CreateOrderRequest;
import com.beratoztas.dto.request.OrderStatusUpdateRequest;
import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.response.OrderResponse;
import com.beratoztas.dto.response.PageResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IOrderService {

	public OrderResponse getOrderById(Long orderId,JwtUserDetails userDetails);

	public PageResponse<OrderResponse> getMyOrders(JwtUserDetails userDetails,RestPageRequest request);

	public PageResponse<OrderResponse> getAllOrders(RestPageRequest request);

	public OrderResponse createOrderFromCart(JwtUserDetails userDetails,CreateOrderRequest request);

	public OrderResponse updateOrderStatus(Long orderId,OrderStatusUpdateRequest newStatus);

}
