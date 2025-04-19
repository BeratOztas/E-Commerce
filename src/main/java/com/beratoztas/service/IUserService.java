package com.beratoztas.service;

import com.beratoztas.dto.request.RestPageRequest;
import com.beratoztas.dto.request.UpdateUserRequest;
import com.beratoztas.dto.response.PageResponse;
import com.beratoztas.dto.response.UserResponse;

public interface IUserService {

	public UserResponse getUserById(Long id);

	public PageResponse<UserResponse> getAllUsers(RestPageRequest request);

	public UserResponse updateUserById(Long id, UpdateUserRequest request);

	public void deleteUserById(Long id);
}
