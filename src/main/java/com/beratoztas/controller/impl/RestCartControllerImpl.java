package com.beratoztas.controller.impl;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beratoztas.controller.ApiResponse;
import com.beratoztas.controller.IRestCartController;
import com.beratoztas.controller.RestBaseController;
import com.beratoztas.requests.AddCartItemRequest;
import com.beratoztas.requests.UpdateCartItemRequest;
import com.beratoztas.responses.CartResponse;
import com.beratoztas.security.JwtUserDetails;
import com.beratoztas.service.ICartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class RestCartControllerImpl extends RestBaseController implements IRestCartController {

	private ICartService cartService;
	
	public RestCartControllerImpl(ICartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping
	@Override
	public ApiResponse<CartResponse> getMyCart(@AuthenticationPrincipal JwtUserDetails userDetails) {
		return ok(cartService.getMyCart(userDetails));
	}
	
	@PostMapping("/items")
	@Override
	public ApiResponse<CartResponse> addItemToCart(@AuthenticationPrincipal JwtUserDetails userDetails,@RequestBody @Valid AddCartItemRequest request) {
		return ok(cartService.addItemToCart(userDetails, request));
	}

	@PutMapping("/items/{cartItemId}")
	@Override
	public ApiResponse<CartResponse> updateCartItemQuantity(@AuthenticationPrincipal JwtUserDetails userDetails,@PathVariable Long cartItemId,
			@RequestBody @Valid UpdateCartItemRequest request) {
		return ok(cartService.updateCartItemQuantity(userDetails, cartItemId, request));
	}

	
	@DeleteMapping("/items/{cartItemId}")
	@Override
	public ApiResponse<?> removeCartItem(@AuthenticationPrincipal JwtUserDetails userDetails,@PathVariable Long cartItemId) {
		cartService.removeCartItem(userDetails, cartItemId);
		return ApiResponse.ok("Cart item removed successfully");
	}

	@DeleteMapping
	@Override
	public ApiResponse<?> clearCart(@AuthenticationPrincipal JwtUserDetails userDetails) {
		cartService.clearCart(userDetails);
		return ApiResponse.ok("Cart cleared successfully");
	}

}
