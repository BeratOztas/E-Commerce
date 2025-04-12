package com.beratoztas.controllerimpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beratoztas.controller.IRestCategoryController;
import com.beratoztas.requests.CategoryRequest;
import com.beratoztas.responses.CategoryResponse;
import com.beratoztas.service.ICategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class RestCategoryController implements IRestCategoryController {

	private ICategoryService categoryService;
	
	public RestCategoryController(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	@GetMapping
	@Override
	public ResponseEntity<List<CategoryResponse>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
	}

	@PutMapping("/{id}")
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryResponse> updateCategoryById(@PathVariable(name = "id") Long id,@RequestBody @Valid CategoryRequest request) {
		return ResponseEntity.ok(categoryService.updateCategoryById(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
		categoryService.deleteCategoryById(id);
		return ResponseEntity.ok("Category Deleted.");
	}

}
