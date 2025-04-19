package com.beratoztas.service;

import com.beratoztas.dto.request.LoginRequest;
import com.beratoztas.dto.request.LogoutTokenRequest;
import com.beratoztas.dto.request.RefreshTokenRequest;
import com.beratoztas.dto.request.RegisterRequest;
import com.beratoztas.dto.response.AuthResponse;

public interface IAuthenticationService {

	public AuthResponse register(RegisterRequest request);

	public AuthResponse login(LoginRequest request);

	public AuthResponse refresh(RefreshTokenRequest request);

	public void logout(LogoutTokenRequest request);
}
