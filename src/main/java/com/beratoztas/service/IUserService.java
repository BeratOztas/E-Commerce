package com.beratoztas.service;

import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.requests.UpdateUserRequest;
import com.beratoztas.responses.PageResponse;
import com.beratoztas.responses.UserResponse;

public interface IUserService {

	public UserResponse getUserById(Long id);

	public PageResponse<UserResponse> getAllUsers(RestPageRequest request);

	public UserResponse updateUserById(Long id, UpdateUserRequest request);

	public void deleteUserById(Long id);
}
