package com.beratoztas.controller;

import org.springframework.http.ResponseEntity;

import com.beratoztas.dto.request.LoginRequest;
import com.beratoztas.dto.request.LogoutTokenRequest;
import com.beratoztas.dto.request.RefreshTokenRequest;
import com.beratoztas.dto.request.RegisterRequest;
import com.beratoztas.dto.response.AuthResponse;

public interface IRestAuthenticationController {

	public ResponseEntity<ApiResponse<AuthResponse>> register(RegisterRequest request);

	public ResponseEntity<ApiResponse<AuthResponse>> login(LoginRequest request);

	public ResponseEntity<ApiResponse<AuthResponse>> refresh(RefreshTokenRequest request);

	public ResponseEntity<ApiResponse<?>> logout(LogoutTokenRequest request);
}
