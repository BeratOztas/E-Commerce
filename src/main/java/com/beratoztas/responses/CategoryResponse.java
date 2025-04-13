package com.beratoztas.responses;

import com.beratoztas.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse extends BaseDto {

	private String name;
	
	//private List<ProductResponse> products;
	
	public CategoryResponse(Category category) {
		this.setId(category.getId());
		this.setCreatedTime(category.getCreatedTime());
		this.name=category.getName();
	}
}
