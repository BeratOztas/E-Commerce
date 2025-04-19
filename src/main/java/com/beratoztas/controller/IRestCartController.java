package com.beratoztas.controller;

import com.beratoztas.dto.request.AddCartItemRequest;
import com.beratoztas.dto.request.UpdateCartItemRequest;
import com.beratoztas.dto.response.CartResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IRestCartController {

	public ApiResponse<CartResponse> getMyCart(JwtUserDetails userDetails);

	public ApiResponse<CartResponse> addItemToCart(JwtUserDetails userDetails, AddCartItemRequest request);

	public ApiResponse<CartResponse> updateCartItemQuantity(JwtUserDetails userDetails,Long cartItemId, UpdateCartItemRequest request);

	public ApiResponse<?> removeCartItem(JwtUserDetails userDetails, Long cartItemId);

	public ApiResponse<?> clearCart(JwtUserDetails userDetails);

}
