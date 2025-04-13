package com.beratoztas.controllerimpl;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.beratoztas.controller.IRestProductController;
import com.beratoztas.requests.ProductRequest;
import com.beratoztas.responses.ProductResponse;
import com.beratoztas.service.IProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class RestProductControllerImpl implements IRestProductController {

	private IProductService productService;

	public RestProductControllerImpl(IProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<ProductResponse> getProductById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}

	@GetMapping
	@Override
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<ProductResponse> updateProductById(@PathVariable(name = "id") Long id,
			@RequestBody @Valid ProductRequest request) {
		return ResponseEntity.ok(productService.updateProductById(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<?> deleteProductById(@PathVariable(name = "id") Long id) {
		productService.deleteProductById(id);
		return ResponseEntity.ok("Product deleted succesfully.");
	}

}
