package com.beratoztas.dto.response;

import java.math.BigDecimal;

import com.beratoztas.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse extends BaseDto {

	private String name;

	private String description;

	private BigDecimal price;

	private Integer stock;

	private String categoryName;

	public ProductResponse(Product product) {
		this.setId(product.getId());
		this.setCreatedTime(product.getCreatedTime());
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.stock = product.getStock();
		this.categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
	}

}
