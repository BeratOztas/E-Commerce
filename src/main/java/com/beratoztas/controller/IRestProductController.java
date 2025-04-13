package com.beratoztas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.beratoztas.requests.ProductRequest;
import com.beratoztas.responses.ProductResponse;

public interface IRestProductController {

	public ResponseEntity<ProductResponse> getProductById(Long id);

	public ResponseEntity<List<ProductResponse>> getAllProducts();

	public ResponseEntity<ProductResponse> createProduct(ProductRequest request);

	public ResponseEntity<ProductResponse> updateProductById(Long id, ProductRequest request);

	public ResponseEntity<?> deleteProductById(Long id);

}
