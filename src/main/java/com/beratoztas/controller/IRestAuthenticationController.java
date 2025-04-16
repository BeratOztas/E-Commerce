package com.beratoztas.controller;

import com.beratoztas.requests.LoginRequest;
import com.beratoztas.requests.RefreshTokenRequest;
import com.beratoztas.requests.RegisterRequest;
import com.beratoztas.responses.AuthResponse;

public interface IRestAuthenticationController {

	public ApiResponse<AuthResponse> register(RegisterRequest request);

	public ApiResponse<AuthResponse> login(LoginRequest request);

	public ApiResponse<AuthResponse> refresh(RefreshTokenRequest request);

	public ApiResponse<?> logout(RefreshTokenRequest request);
}
