package com.beratoztas.controller;

import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.requests.UpdateUserRequest;
import com.beratoztas.responses.PageResponse;
import com.beratoztas.responses.UserResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IRestUserController {

	public ApiResponse<UserResponse> getMe(JwtUserDetails userDetails);

	public ApiResponse<UserResponse> getUserById(Long id);

	public ApiResponse<PageResponse<UserResponse>> getAllUsers(RestPageRequest request);

	public ApiResponse<UserResponse> updateMe(JwtUserDetails userDetails, UpdateUserRequest request);

	public ApiResponse<UserResponse> updateUserById(Long id, UpdateUserRequest request);

	public ApiResponse<?> deleteMe(JwtUserDetails userDetails);

	public ApiResponse<?> deleteUserById(Long id);
}
