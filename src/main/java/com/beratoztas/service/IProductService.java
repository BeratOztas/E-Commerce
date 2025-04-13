package com.beratoztas.service;

import java.util.List;

import com.beratoztas.requests.ProductRequest;
import com.beratoztas.responses.ProductResponse;

public interface IProductService {

	public ProductResponse getProductById(Long id);

	public List<ProductResponse> getAllProducts();

	public ProductResponse createProduct(ProductRequest request);

	public ProductResponse updateProductById(Long id, ProductRequest request);

	public void deleteProductById(Long id);

}
