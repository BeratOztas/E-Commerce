package com.beratoztas.dto.response;

import com.beratoztas.entities.CartItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse extends BaseDto {

	private Integer quantity;

	private ProductResponse product;

	public CartItemResponse(CartItem cartItem) {
		this.setId(cartItem.getId());
		this.setCreatedTime(cartItem.getCreatedTime());
		this.quantity = cartItem.getQuantity();
		this.product = new ProductResponse(cartItem.getProduct());
	}
}
