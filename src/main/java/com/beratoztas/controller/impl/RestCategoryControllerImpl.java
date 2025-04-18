package com.beratoztas.controller.impl;

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
import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.responses.CategoryResponse;
import com.beratoztas.responses.PageResponse;
import com.beratoztas.service.ICategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Category", description = "Category Management ")
@RestController
@RequestMapping("/categories")
public class RestCategoryControllerImpl extends RestBaseController implements IRestCategoryController {

	private ICategoryService categoryService;
	
	public RestCategoryControllerImpl(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Operation(summary = "Get category by ID", description = "Retrieves the details of a category by its ID.")
	@GetMapping("/{id}")
	@Override
	public ApiResponse<CategoryResponse> getCategoryById(@PathVariable(name = "id") Long id) {
		return ok(categoryService.getCategoryById(id));
	}

	@Operation(summary = "Get all categories (paginated)", description = "Returns a paginated list of all available categories.")
	@GetMapping
	@Override
	public ApiResponse<PageResponse<CategoryResponse>> getAllCategories(RestPageRequest request) {
		return ok(categoryService.getAllCategories(request));
	}

	@Operation(summary = "Create new category", description = "Creates a new category in the system. Accessible by users with 'ADMIN' role.")
	@PostMapping
	@SecurityRequirement(name = "BearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
		return ok(categoryService.createCategory(request));
	}

	@Operation(summary = "Update category by ID", description = "Updates the details of an existing category by its ID. Accessible by users with 'ADMIN' role.")
	@PutMapping("/{id}")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<CategoryResponse> updateCategoryById(@PathVariable(name = "id") Long id,@RequestBody @Valid CategoryRequest request) {
		return ok(categoryService.updateCategoryById(id, request));
	}

	@Operation(summary = "Delete category by ID", description = "Deletes a category by its ID. Accessible by users with 'ADMIN' role.")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<?> deleteCategoryById(@PathVariable Long id) {
		categoryService.deleteCategoryById(id);
		return ApiResponse.ok("Category Deleted.");
	}

}
