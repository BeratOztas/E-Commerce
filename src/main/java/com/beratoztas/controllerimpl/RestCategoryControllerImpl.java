package com.beratoztas.controllerimpl;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beratoztas.controller.ApiResponse;
import com.beratoztas.controller.IRestCategoryController;
import com.beratoztas.controller.RestBaseController;
import com.beratoztas.requests.CategoryRequest;
import com.beratoztas.responses.CategoryResponse;
import com.beratoztas.service.ICategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class RestCategoryControllerImpl extends RestBaseController implements IRestCategoryController {

	private ICategoryService categoryService;
	
	public RestCategoryControllerImpl(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/{id}")
	@Override
	public ApiResponse<CategoryResponse> getCategoryById(@PathVariable(name = "id") Long id) {
		return ok(categoryService.getCategoryById(id));
	}

	@GetMapping
	@Override
	public ApiResponse<List<CategoryResponse>> getAllCategories() {
		return ok(categoryService.getAllCategories());
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
		return ok(categoryService.createCategory(request));
	}

	@PutMapping("/{id}")
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<CategoryResponse> updateCategoryById(@PathVariable(name = "id") Long id,@RequestBody @Valid CategoryRequest request) {
		return ok(categoryService.updateCategoryById(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ApiResponse<?> deleteCategoryById(@PathVariable Long id) {
		categoryService.deleteCategoryById(id);
		return ApiResponse.ok("Category Deleted.");
	}

}
