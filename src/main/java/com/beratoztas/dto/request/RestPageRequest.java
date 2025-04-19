package com.beratoztas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPageRequest {

	private Integer pageNumber;

	private Integer pageSize;

	private String sortBy;

	private boolean asc =true;
}
