package com.beratoztas.controller;

import java.util.List;

import com.beratoztas.requests.UpdateUserRequest;
import com.beratoztas.responses.UserResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IRestUserController {

	public ApiResponse<UserResponse> getMe(JwtUserDetails userDetails);

	public ApiResponse<UserResponse> getUserById(Long id);

	public ApiResponse<List<UserResponse>> getAllUsers();

	public ApiResponse<UserResponse> updateMe(JwtUserDetails userDetails, UpdateUserRequest request);

	public ApiResponse<UserResponse> updateUserById(Long id, UpdateUserRequest request);

	public ApiResponse<?> deleteMe(JwtUserDetails userDetails);

	public ApiResponse<?> deleteUserById(Long id);
}
