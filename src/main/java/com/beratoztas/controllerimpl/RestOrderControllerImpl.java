package com.beratoztas.controllerimpl;

import java.lang.annotation.Retention;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beratoztas.controller.IRestOrderController;
import com.beratoztas.enums.CartStatus;
import com.beratoztas.requests.CreateOrderRequest;
import com.beratoztas.requests.OrderStatusUpdateRequest;
import com.beratoztas.responses.OrderResponse;
import com.beratoztas.security.JwtUserDetails;
import com.beratoztas.service.IOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class RestOrderControllerImpl implements IRestOrderController {
	
	private IOrderService orderService;
	
	public RestOrderControllerImpl(IOrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/{orderId}")
	@PreAuthorize("hasRole('USER')or ('ADMIN')")
	@Override
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId,@AuthenticationPrincipal JwtUserDetails userDetails) {
		return ResponseEntity.ok(orderService.getOrderById(orderId, userDetails));
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<List<OrderResponse>> getMyOrders(@AuthenticationPrincipal JwtUserDetails userDetails) {
		return ResponseEntity.ok(orderService.getMyOrders(userDetails));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<List<OrderResponse>> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<OrderResponse> createOrderFromCart(@AuthenticationPrincipal JwtUserDetails userDetails,@RequestBody @Valid CreateOrderRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrderFromCart(userDetails, request));
	}

	@PutMapping("/{orderId}/status")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId,@RequestBody @Valid OrderStatusUpdateRequest newStatus) {
		return ResponseEntity.ok(orderService.updateOrderStatus(orderId, newStatus));
	}

}
