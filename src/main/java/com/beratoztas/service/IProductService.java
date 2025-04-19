package com.beratoztas.service;

import com.beratoztas.dto.request.ProductRequest;
import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.response.PageResponse;
import com.beratoztas.dto.response.ProductResponse;

public interface IProductService {

	public ProductResponse getProductById(Long id);

	public PageResponse<ProductResponse> getAllProducts(RestPageRequest request);

	public ProductResponse createProduct(ProductRequest request);

	public ProductResponse updateProductById(Long id, ProductRequest request);

	public void deleteProductById(Long id);

}
