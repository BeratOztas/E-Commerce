package com.beratoztas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.beratoztas.dto.request.CategoryRequest;
import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.response.CategoryResponse;
import com.beratoztas.dto.response.PageResponse;
import com.beratoztas.entities.Category;
import com.beratoztas.exception.BaseException;
import com.beratoztas.exception.ErrorMessage;
import com.beratoztas.exception.MessageType;
import com.beratoztas.repository.CategoryRepository;
import com.beratoztas.service.ICategoryService;
import com.beratoztas.utils.PageUtil;

@Service
public class CategoryServiceImpl implements ICategoryService {

	private CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	private Category findCategoryById(Long id) {
	    return categoryRepository.findById(id)
	        .orElseThrow(() -> new BaseException(
	            new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, "Category Id : " + id)));
	}

	@Override
	public CategoryResponse getCategoryById(Long id) {
		Category category = findCategoryById(id);

		return new CategoryResponse(category);
	}

	@Override
	public PageResponse<CategoryResponse> getAllCategories(RestPageRequest  request) {
		Pageable pageable = PageUtil.toPageable(request);
		Page<Category> page =categoryRepository.findAll(pageable);

		if (page.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CATEGORIES_NOT_FOUND, ""));
		}
		List<CategoryResponse> content =page.getContent()
				.stream()
				.map(CategoryResponse::new)
				.collect(Collectors.toList());

		return PageUtil.toPageResponse(page, content);
	}

	@Override
	public CategoryResponse createCategory(CategoryRequest request) {
		Category category = new Category();
		category.setName(request.getName());
		return new CategoryResponse(categoryRepository.save(category));
	}

	@Override
	public CategoryResponse updateCategoryById(Long id, CategoryRequest request) {
		Category category = findCategoryById(id);

		category.setName(request.getName());

		return new CategoryResponse(categoryRepository.save(category));

	}

	@Override
	public void deleteCategoryById(Long id) {
		Category category = findCategoryById(id);

		categoryRepository.delete(category);
	}

}
