package com.beratoztas.service;

import com.beratoztas.requests.CategoryRequest;
import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.responses.CategoryResponse;
import com.beratoztas.responses.PageResponse;

public interface ICategoryService {

	public CategoryResponse getCategoryById(Long id);
	
	public PageResponse<CategoryResponse> getAllCategories(RestPageRequest request);
	
	public CategoryResponse createCategory(CategoryRequest request);
	
	public CategoryResponse updateCategoryById(Long id,CategoryRequest request);
	
	public void deleteCategoryById(Long id);
	
}
