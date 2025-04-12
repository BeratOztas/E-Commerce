package com.beratoztas.service;

import java.util.List;

import com.beratoztas.requests.UpdateUserRequest;
import com.beratoztas.responses.UserResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IUserService {

	public UserResponse getUserById(Long id);

	public List<UserResponse> getAllUsers();

	public UserResponse updateUserById(Long id, UpdateUserRequest request);

	public void deleteUserById(Long id);
}
