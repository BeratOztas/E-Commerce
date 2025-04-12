package com.beratoztas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.beratoztas.requests.UpdateUserRequest;
import com.beratoztas.responses.UserResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IRestUserController {

	public ResponseEntity<UserResponse> getMe(JwtUserDetails userDetails);

	public ResponseEntity<UserResponse> getUserById(Long id);

	public ResponseEntity<List<UserResponse>> getAllUsers();

	public ResponseEntity<UserResponse> updateMe(JwtUserDetails userDetails, UpdateUserRequest request);

	public ResponseEntity<UserResponse> updateUserById(Long id, UpdateUserRequest request);

	public ResponseEntity<?> deleteMe(JwtUserDetails userDetails);

	public ResponseEntity<?> deleteUserById(Long id);
}
