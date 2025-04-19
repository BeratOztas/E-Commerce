package com.beratoztas.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.beratoztas.entities.Cart;
import com.beratoztas.enums.CartStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse extends BaseDto {

	private CartStatus status;

	private List<CartItemResponse> cartItems;

	public CartResponse(Cart cart) {
		this.setId(cart.getId());
		this.setCreatedTime(cart.getCreatedTime());
		this.status =cart.getStatus();

		if(cart.getCartItems() !=null) {
			this.cartItems =cart.getCartItems().stream()
					.map(cartItem -> new CartItemResponse(cartItem)).collect(Collectors.toList());
		}
	}
}

