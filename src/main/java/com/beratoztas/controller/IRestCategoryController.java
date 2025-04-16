package com.beratoztas.controller;

import java.util.List;

import com.beratoztas.requests.CategoryRequest;
import com.beratoztas.responses.CategoryResponse;

public interface IRestCategoryController {

	public ApiResponse<CategoryResponse> getCategoryById(Long id);

	public ApiResponse<List<CategoryResponse>> getAllCategories();

	public ApiResponse<CategoryResponse> createCategory(CategoryRequest request);

	public ApiResponse<CategoryResponse> updateCategoryById(Long id, CategoryRequest request);

	public ApiResponse<?> deleteCategoryById(Long id);

}
