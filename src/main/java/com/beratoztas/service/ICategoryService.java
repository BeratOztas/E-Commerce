package com.beratoztas.service;

import com.beratoztas.dto.request.CategoryRequest;
import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.response.CategoryResponse;
import com.beratoztas.dto.response.PageResponse;

public interface ICategoryService {

	public CategoryResponse getCategoryById(Long id);

	public PageResponse<CategoryResponse> getAllCategories(RestPageRequest request);

	public CategoryResponse createCategory(CategoryRequest request);

	public CategoryResponse updateCategoryById(Long id,CategoryRequest request);

	public void deleteCategoryById(Long id);

}
