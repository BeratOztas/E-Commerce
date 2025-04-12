package com.beratoztas.service;

import java.util.List;

import com.beratoztas.requests.CategoryRequest;
import com.beratoztas.responses.CategoryResponse;

public interface ICategoryService {

	public CategoryResponse getCategoryById(Long id);
	
	public List<CategoryResponse> getAllCategories();
	
	public CategoryResponse createCategory(CategoryRequest request);
	
	public CategoryResponse updateCategoryById(Long id,CategoryRequest request);
	
	public void deleteCategoryById(Long id);
	
}
