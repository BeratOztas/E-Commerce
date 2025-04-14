package com.beratoztas.service;

import com.beratoztas.requests.AddCartItemRequest;
import com.beratoztas.requests.UpdateCartItemRequest;
import com.beratoztas.responses.CartResponse;
import com.beratoztas.security.JwtUserDetails;

public interface ICartService {

	public CartResponse getMyCart(JwtUserDetails userDetails);
	
	public CartResponse addItemToCart(JwtUserDetails userDetails,AddCartItemRequest request);
	
	public CartResponse updateCartItemQuantity(JwtUserDetails userDetails,Long cartItemId, UpdateCartItemRequest request);
	
	public void removeCartItem(JwtUserDetails userDetails,Long cartItemId);
	
	public void clearCart(JwtUserDetails userDetails);
}
