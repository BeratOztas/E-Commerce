package com.beratoztas.controller;

import com.beratoztas.requests.AddCartItemRequest;
import com.beratoztas.requests.UpdateCartItemRequest;
import com.beratoztas.responses.CartResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IRestCartController {
	
	public ApiResponse<CartResponse> getMyCart(JwtUserDetails userDetails);

	public ApiResponse<CartResponse> addItemToCart(JwtUserDetails userDetails, AddCartItemRequest request);

	public ApiResponse<CartResponse> updateCartItemQuantity(JwtUserDetails userDetails,Long cartItemId, UpdateCartItemRequest request);

	public ApiResponse<?> removeCartItem(JwtUserDetails userDetails, Long cartItemId);

	public ApiResponse<?> clearCart(JwtUserDetails userDetails);
	
}
