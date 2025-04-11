package com.beratoztas.responses;

import java.util.Date;

import lombok.Data;

@Data
public class BaseDto {

	private Long id;
	
	private Date createdTime;
}
