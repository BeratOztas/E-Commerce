package com.beratoztas.controller.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.beratoztas.controller.IRestProductController;
import com.beratoztas.controller.RestBaseController;
import com.beratoztas.entities.Product;
import com.beratoztas.requests.ProductRequest;
import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.responses.PageResponse;
import com.beratoztas.responses.ProductResponse;
import com.beratoztas.service.IProductService;
import com.beratoztas.utils.PageUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class RestProductControllerImpl extends RestBaseController implements IRestProductController {

	private IProductService productService;

	public RestProductControllerImpl(IProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/{id}")
	@Override
	public ApiResponse<ProductResponse> getProductById(@PathVariable(name = "id") Long id) {
		return ok(productService.getProductById(id));
	}

	@GetMapping
	@Override
	public ApiResponse<PageResponse<ProductResponse>> getAllProducts(RestPageRequest request) {
		PageResponse<ProductResponse> response = productService.getAllProducts(request);
		return ApiResponse.ok(response);	
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
		return ok(productService.createProduct(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ApiResponse<ProductResponse> updateProductById(@PathVariable(name = "id") Long id,
			@RequestBody @Valid ProductRequest request) {
		return ok(productService.updateProductById(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ApiResponse<?> deleteProductById(@PathVariable(name = "id") Long id) {
		productService.deleteProductById(id);
		return ApiResponse.ok("Product deleted succesfully.");
	}


}
