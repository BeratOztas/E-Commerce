package com.beratoztas.service;

import com.beratoztas.requests.ProductRequest;
import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.responses.PageResponse;
import com.beratoztas.responses.ProductResponse;

public interface IProductService {

	public ProductResponse getProductById(Long id);

	public PageResponse<ProductResponse> getAllProducts(RestPageRequest request);

	public ProductResponse createProduct(ProductRequest request);

	public ProductResponse updateProductById(Long id, ProductRequest request);

	public void deleteProductById(Long id);

}
