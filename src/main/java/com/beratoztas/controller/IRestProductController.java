package com.beratoztas.controller;

import com.beratoztas.dto.request.ProductRequest;
import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.response.PageResponse;
import com.beratoztas.dto.response.ProductResponse;

public interface IRestProductController {

	public ApiResponse<ProductResponse> getProductById(Long id);

	public ApiResponse<PageResponse<ProductResponse>> getAllProducts(RestPageRequest request);

	public ApiResponse<ProductResponse> createProduct(ProductRequest request);

	public ApiResponse<ProductResponse> updateProductById(Long id, ProductRequest request);

	public ApiResponse<?> deleteProductById(Long id);

}
