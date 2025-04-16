package com.beratoztas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.beratoztas.requests.ProductRequest;
import com.beratoztas.responses.ProductResponse;

public interface IRestProductController {

	public ApiResponse<ProductResponse> getProductById(Long id);

	public ApiResponse<List<ProductResponse>> getAllProducts();

	public ApiResponse<ProductResponse> createProduct(ProductRequest request);

	public ApiResponse<ProductResponse> updateProductById(Long id, ProductRequest request);

	public ApiResponse<?> deleteProductById(Long id);

}
