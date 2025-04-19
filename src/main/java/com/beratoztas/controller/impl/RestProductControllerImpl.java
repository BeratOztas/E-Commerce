package com.beratoztas.controller.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beratoztas.controller.ApiResponse;
import com.beratoztas.controller.IRestProductController;
import com.beratoztas.controller.RestBaseController;
import com.beratoztas.dto.request.ProductRequest;
import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.response.PageResponse;
import com.beratoztas.dto.response.ProductResponse;
import com.beratoztas.service.IProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Product", description = "Product Management")
@RestController
@RequestMapping("/products")
public class RestProductControllerImpl extends RestBaseController implements IRestProductController {

	private IProductService productService;

	public RestProductControllerImpl(IProductService productService) {
		this.productService = productService;
	}

	@Operation(summary = "Get product by ID", description = "Retrieves product details by product ID.")
	@GetMapping("/{id}")
	@Override
	public ApiResponse<ProductResponse> getProductById(@PathVariable(name = "id") Long id) {
		return ok(productService.getProductById(id));
	}


	@Operation(summary = "Get all products (paginated)", description = "Returns a paginated list of all available products.")
	@GetMapping
	@Override
	public ApiResponse<PageResponse<ProductResponse>> getAllProducts(@ModelAttribute RestPageRequest request) {
		return ApiResponse.ok(productService.getAllProducts(request));
	}

	@Operation(summary = "Create a new product", description = "Creates a new product based on the provided product details. Only accessible by users with 'ADMIN' role.")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
		return ok(productService.createProduct(request));
	}

	@Operation(summary = "Update product by ID", description = "Updates the product details for the specified product ID. Only accessible by users with 'ADMIN' role.")
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<ProductResponse> updateProductById(@PathVariable(name = "id") Long id,
			@RequestBody @Valid ProductRequest request) {
		return ok(productService.updateProductById(id, request));
	}

	@Operation(summary = "Delete product by ID", description = "Deletes the product with the specified ID. Only accessible by users with 'ADMIN' role.")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<?> deleteProductById(@PathVariable(name = "id") Long id) {
		productService.deleteProductById(id);
		return ApiResponse.ok("Product deleted succesfully.");
	}


}
