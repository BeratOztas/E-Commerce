package com.beratoztas.controller.impl;

import java.util.List;import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.responses.OrderResponse;
import com.beratoztas.responses.PageResponse;
import com.beratoztas.security.JwtUserDetails;
import com.beratoztas.service.IOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Order", description = "User order management")
@RestController
@RequestMapping("/orders")
public class RestOrderControllerImpl extends RestBaseController implements IRestOrderController {

	private IOrderService orderService;

	public RestOrderControllerImpl(IOrderService orderService) {
		this.orderService = orderService;
	}

	@Operation(summary = "Get order by ID", description = "Retrieves the details of an order by its ID. Accessible by users with 'USER' or 'ADMIN' role.")
	@GetMapping("/{orderId}")
	@SecurityRequirement(name = "BearerAuth")
	@PreAuthorize("hasRole('USER')or ('ADMIN')")
	@Override
	public ApiResponse<OrderResponse> getOrderById(@PathVariable Long orderId,
			@AuthenticationPrincipal JwtUserDetails userDetails) {
		return ok(orderService.getOrderById(orderId, userDetails));
	}
	
	@Operation(summary = "Get user orders ", description = "Retrieves paginated list of authenticated user's past orders.")
	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<PageResponse<OrderResponse>> getMyOrders(@AuthenticationPrincipal JwtUserDetails userDetails,@ModelAttribute RestPageRequest request) {
		 return ApiResponse.ok(orderService.getMyOrders(userDetails, request));
	}

	@Operation(summary = "Get all orders (paginated)", description = "Returns a paginated list of all orders. Accessible by 'ADMIN' role.")
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<PageResponse<OrderResponse>> getAllOrders(@ModelAttribute RestPageRequest request) {
		return ApiResponse.ok(orderService.getAllOrders(request));
	}

	@Operation(summary = "Create new order", description = "Creates a new order from the user's current cart.")
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<OrderResponse> createOrderFromCart(@AuthenticationPrincipal JwtUserDetails userDetails,
			@RequestBody @Valid CreateOrderRequest request) {
		return ok(orderService.createOrderFromCart(userDetails, request));
	}

	@Operation(summary = "Update order status", description = "Updates the status of an order. Accessible only by 'ADMIN' role.")
	@PutMapping("/{orderId}/status")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<OrderResponse> updateOrderStatus(@PathVariable Long orderId,
			@RequestBody @Valid OrderStatusUpdateRequest newStatus) {
		return ok(orderService.updateOrderStatus(orderId, newStatus));
	}

}
