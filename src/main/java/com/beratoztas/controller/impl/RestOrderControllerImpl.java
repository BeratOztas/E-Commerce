package com.beratoztas.controller.impl;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beratoztas.controller.ApiResponse;
import com.beratoztas.controller.IRestOrderController;
import com.beratoztas.controller.RestBaseController;
import com.beratoztas.requests.CreateOrderRequest;
import com.beratoztas.requests.OrderStatusUpdateRequest;
import com.beratoztas.responses.OrderResponse;
import com.beratoztas.security.JwtUserDetails;
import com.beratoztas.service.IOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class RestOrderControllerImpl extends RestBaseController implements IRestOrderController {
	
	private IOrderService orderService;
	
	public RestOrderControllerImpl(IOrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/{orderId}")
	@PreAuthorize("hasRole('USER')or ('ADMIN')")
	@Override
	public ApiResponse<OrderResponse> getOrderById(@PathVariable Long orderId,@AuthenticationPrincipal JwtUserDetails userDetails) {
		return ok(orderService.getOrderById(orderId, userDetails));
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	@Override
	public ApiResponse<List<OrderResponse>> getMyOrders(@AuthenticationPrincipal JwtUserDetails userDetails) {
		return ok(orderService.getMyOrders(userDetails));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ApiResponse<List<OrderResponse>> getAllOrders() {
		return ok(orderService.getAllOrders());
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	@Override
	public ApiResponse<OrderResponse> createOrderFromCart(@AuthenticationPrincipal JwtUserDetails userDetails,@RequestBody @Valid CreateOrderRequest request) {
		return ok(orderService.createOrderFromCart(userDetails, request));
	}

	@PutMapping("/{orderId}/status")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ApiResponse<OrderResponse> updateOrderStatus(@PathVariable Long orderId,@RequestBody @Valid OrderStatusUpdateRequest newStatus) {
		return ok(orderService.updateOrderStatus(orderId, newStatus));
	}

}
