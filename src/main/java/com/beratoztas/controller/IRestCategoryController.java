package com.beratoztas.controller;

import com.beratoztas.dto.request.CategoryRequest;
import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.response.CategoryResponse;
import com.beratoztas.dto.response.PageResponse;

public interface IRestCategoryController {

	public ApiResponse<CategoryResponse> getCategoryById(Long id);

	public ApiResponse<PageResponse<CategoryResponse>> getAllCategories(RestPageRequest request);

	public ApiResponse<CategoryResponse> createCategory(CategoryRequest request);

	public ApiResponse<CategoryResponse> updateCategoryById(Long id, CategoryRequest request);

	public ApiResponse<?> deleteCategoryById(Long id);

}
