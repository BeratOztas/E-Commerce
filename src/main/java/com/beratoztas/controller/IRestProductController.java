package com.beratoztas.controller;

import com.beratoztas.requests.ProductRequest;
import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.responses.PageResponse;
import com.beratoztas.responses.ProductResponse;

public interface IRestProductController {

	public ApiResponse<ProductResponse> getProductById(Long id);

	public ApiResponse<PageResponse<ProductResponse>> getAllProducts(RestPageRequest request);

	public ApiResponse<ProductResponse> createProduct(ProductRequest request);

	public ApiResponse<ProductResponse> updateProductById(Long id, ProductRequest request);

	public ApiResponse<?> deleteProductById(Long id);

}
