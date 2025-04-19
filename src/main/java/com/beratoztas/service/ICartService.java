package com.beratoztas.service;

import com.beratoztas.dto.request.AddCartItemRequest;
import com.beratoztas.dto.request.UpdateCartItemRequest;
import com.beratoztas.dto.response.CartResponse;
import com.beratoztas.security.JwtUserDetails;

public interface ICartService {

	public CartResponse getMyCart(JwtUserDetails userDetails);

	public CartResponse addItemToCart(JwtUserDetails userDetails,AddCartItemRequest request);

	public CartResponse updateCartItemQuantity(JwtUserDetails userDetails,Long cartItemId, UpdateCartItemRequest request);

	public void removeCartItem(JwtUserDetails userDetails,Long cartItemId);

	public void clearCart(JwtUserDetails userDetails);
}
