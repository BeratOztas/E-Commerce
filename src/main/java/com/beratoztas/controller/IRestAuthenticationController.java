package com.beratoztas.controller;

import org.springframework.http.ResponseEntity;

import com.beratoztas.requests.LoginRequest;
import com.beratoztas.requests.LogoutTokenRequest;
import com.beratoztas.requests.RefreshTokenRequest;
import com.beratoztas.requests.RegisterRequest;
import com.beratoztas.responses.AuthResponse;

public interface IRestAuthenticationController {

	public ResponseEntity<ApiResponse<AuthResponse>> register(RegisterRequest request);

	public ResponseEntity<ApiResponse<AuthResponse>> login(LoginRequest request);

	public ResponseEntity<ApiResponse<AuthResponse>> refresh(RefreshTokenRequest request);

	public ResponseEntity<ApiResponse<?>> logout(LogoutTokenRequest request);
}
