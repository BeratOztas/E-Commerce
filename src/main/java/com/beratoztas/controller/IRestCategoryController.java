package com.beratoztas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.beratoztas.requests.CategoryRequest;
import com.beratoztas.responses.CategoryResponse;

public interface IRestCategoryController {

	public ResponseEntity<CategoryResponse> getCategoryById(Long id);

	public ResponseEntity<List<CategoryResponse>> getAllCategories();

	public ResponseEntity<CategoryResponse> createCategory(CategoryRequest request);

	public ResponseEntity<CategoryResponse> updateCategoryById(Long id, CategoryRequest request);

	public ResponseEntity<?> deleteCategoryById(Long id);

}
