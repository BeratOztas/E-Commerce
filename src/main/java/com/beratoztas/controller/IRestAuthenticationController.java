package com.beratoztas.controller;

import org.springframework.http.ResponseEntity;

import com.beratoztas.requests.LoginRequest;
import com.beratoztas.requests.RefreshTokenRequest;
import com.beratoztas.requests.RegisterRequest;
import com.beratoztas.responses.AuthResponse;
import com.beratoztas.responses.UserResponse;

public interface IRestAuthenticationController {

	public ResponseEntity<AuthResponse> register(RegisterRequest request);

	public AuthResponse login(LoginRequest request);

	public AuthResponse refresh(RefreshTokenRequest request);

	public void logout(RefreshTokenRequest request);
}
