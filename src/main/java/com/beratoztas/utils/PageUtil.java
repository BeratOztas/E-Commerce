package com.beratoztas.utils;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.response.PageResponse;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageUtil {

	public Pageable toPageable(RestPageRequest request) {

		String sortBy = StringUtils.hasText(request.getSortBy()) ? request.getSortBy() : "id";
		Sort sort = request.isAsc() ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Integer pageNumber =request.getPageNumber() !=null ?request.getPageNumber():0;
		Integer pageSize =request.getPageSize() !=null ?request.getPageSize() :10;

		return PageRequest.of(pageNumber, pageSize, sort);
	}

	public <T> PageResponse<T> toPageResponse(Page<?> page,List<T> content){
		return new PageResponse<>(
				content,
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
				page.getTotalPages(),
				page.isLast()
			);
	}

}
