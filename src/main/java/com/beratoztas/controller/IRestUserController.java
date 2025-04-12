package com.beratoztas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.beratoztas.requests.UpdateUserRequest;
import com.beratoztas.responses.UserResponse;
import com.beratoztas.security.JwtUserDetails;

public interface IRestUserController {

	// Giriş yapan kullanıcı için (GET /me)
	public ResponseEntity<UserResponse> getMe(JwtUserDetails userDetails);

	// Admin veya kullanıcı ID üzerinden (GET /users/{id})
	public ResponseEntity<UserResponse> getUserById(Long id);

	// Sadece admin kullanır (GET /users)
	public ResponseEntity<List<UserResponse>> getAllUsers();

	// Giriş yapan kullanıcı için (Update / me)
	public ResponseEntity<UserResponse> updateMe(JwtUserDetails userDetails, UpdateUserRequest request);

	// Profil güncelleme (PUT /users/{id})
	public ResponseEntity<UserResponse> updateUserById(Long id, UpdateUserRequest request);

	// Giriş yapan kullanıcıyı silme (DELETE /me)
	public ResponseEntity<?> deleteMe(JwtUserDetails userDetails);

	// Kullanıcıyı ID ile silme (DELETE /users/{id})
	public ResponseEntity<?> deleteUserById(Long id);
}
