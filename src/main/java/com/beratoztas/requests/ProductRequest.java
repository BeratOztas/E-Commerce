package com.beratoztas.requests;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

	@NotBlank
	private String name;

	private String description;

	@NotNull
	@Positive
	private BigDecimal price;

	@NotNull
	@Min(0)
	private Integer stock;

	@NotNull
	private Long categoryId;
}
