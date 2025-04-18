package com.beratoztas.controller;

import com.beratoztas.requests.CategoryRequest;
import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.responses.CategoryResponse;
import com.beratoztas.responses.PageResponse;

public interface IRestCategoryController {

	public ApiResponse<CategoryResponse> getCategoryById(Long id);

	public ApiResponse<PageResponse<CategoryResponse>> getAllCategories(RestPageRequest request);

	public ApiResponse<CategoryResponse> createCategory(CategoryRequest request);

	public ApiResponse<CategoryResponse> updateCategoryById(Long id, CategoryRequest request);

	public ApiResponse<?> deleteCategoryById(Long id);

}
