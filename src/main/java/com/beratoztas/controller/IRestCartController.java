package com.beratoztas.controller;

import org.springframework.http.ResponseEntity;

import com.beratoztas.requests.AddCartItemRequest;
import com.beratoztas.requests.UpdateCartItemRequest;
import com.beratoztas.responses.CartResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IRestCartController {
	
	public ResponseEntity<CartResponse> getMyCart(JwtUserDetails userDetails);

	public ResponseEntity<CartResponse> addItemToCart(JwtUserDetails userDetails, AddCartItemRequest request);

	public ResponseEntity<CartResponse> updateCartItemQuantity(JwtUserDetails userDetails,Long cartItemId, UpdateCartItemRequest request);

	public ResponseEntity<?> removeCartItem(JwtUserDetails userDetails, Long cartItemId);

	public ResponseEntity<?> clearCart(JwtUserDetails userDetails);
	
}
