package com.beratoztas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.beratoztas.dto.request.ProductRequest;
import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.response.PageResponse;
import com.beratoztas.dto.response.ProductResponse;
import com.beratoztas.entities.Category;
import com.beratoztas.entities.Product;
import com.beratoztas.exception.BaseException;
import com.beratoztas.exception.ErrorMessage;
import com.beratoztas.exception.MessageType;
import com.beratoztas.repository.CategoryRepository;
import com.beratoztas.repository.ProductRepository;
import com.beratoztas.service.IProductService;
import com.beratoztas.utils.PageUtil;

@Service
public class ProductServiceImpl implements IProductService {

	private ProductRepository productRepository;

	private CategoryRepository categoryRepository;

	public ProductServiceImpl(ProductRepository productRepository,CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository =categoryRepository;
	}

	private Product updateProductFields(ProductRequest request,Category category) {
		Product product =new Product();

		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		product.setCategory(category);

		return product;
	}

	private Product findByProductId(Long id) {
		return productRepository.findById(id)
				.orElseThrow(()-> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, "Product Id : "+id)));
	}

	@Override
	public ProductResponse getProductById(Long id) {
		Product product =findByProductId(id);

		return new ProductResponse(product);
 	}


	@Override
	public PageResponse<ProductResponse> getAllProducts(RestPageRequest request) {
		Pageable pageable =PageUtil.toPageable(request);
		Page<Product> page =productRepository.findAll(pageable);
		if(page.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.PRODUCTS_NOT_FOUND, "No products found."));
		}
		List<ProductResponse> content =page.getContent().stream()
				.map(ProductResponse::new)
				.collect(Collectors.toList());

		return PageUtil.toPageResponse(page, content);
	}


	@Override
	public ProductResponse createProduct(ProductRequest request) {
		Category category =categoryRepository.findById(request.getCategoryId())
				.orElseThrow(()-> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, "Category Id : "+request.getCategoryId())));

		return new ProductResponse(productRepository.save(updateProductFields(request, category)));
	}

	@Override
	public ProductResponse updateProductById(Long id, ProductRequest request) {
		Product product =findByProductId(id);

		Category category =categoryRepository.
				findById(request.getCategoryId()).orElseThrow(() ->new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, "Category Id : "+request.getCategoryId())));

		return new ProductResponse(productRepository.save(updateProductFields(request, category)));
	}

	@Override
	public void deleteProductById(Long id) {
		Product product =findByProductId(id);
		productRepository.delete(product);
	}


}
