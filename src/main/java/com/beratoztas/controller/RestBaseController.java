package com.beratoztas.controller;

public class RestBaseController {

	public <T> ApiResponse<T> ok(T payload){
		return ApiResponse.ok(payload);
	}

	public <T> ApiResponse<T> error(String errorMessage){
		return ApiResponse.error(errorMessage);
	}
}
