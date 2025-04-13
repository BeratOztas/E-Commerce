package com.beratoztas.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.beratoztas.entities.Category;
import com.beratoztas.exception.BaseException;
import com.beratoztas.exception.ErrorMessage;
import com.beratoztas.exception.MessageType;
import com.beratoztas.repository.CategoryRepository;
import com.beratoztas.requests.CategoryRequest;
import com.beratoztas.responses.CategoryResponse;
import com.beratoztas.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	private CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public CategoryResponse getCategoryById(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, "Category Id : " + id)));

		return new CategoryResponse(category);
	}

	@Override
	public List<CategoryResponse> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		if (categories == null || categories.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CATEGORIES_NOT_FOUND, ""));
		}
		return categories.stream().map(category -> new CategoryResponse(category)).collect(Collectors.toList());
	}

	@Override
	public CategoryResponse createCategory(CategoryRequest request) {
		Category category = new Category();
		category.setName(request.getName());
		return new CategoryResponse(categoryRepository.save(category));
	}

	@Override
	public CategoryResponse updateCategoryById(Long id, CategoryRequest request) {
		Category category = categoryRepository.findById(id).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, "Category Id : " + id)));

		category.setName(request.getName());

		return new CategoryResponse(categoryRepository.save(category));

	}

	@Override
	public void deleteCategoryById(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, "Category Id : " + id)));

		categoryRepository.delete(category);
	}

}
