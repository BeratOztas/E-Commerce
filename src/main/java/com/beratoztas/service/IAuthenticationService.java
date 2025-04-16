package com.beratoztas.service;

import com.beratoztas.requests.LoginRequest;
import com.beratoztas.requests.LogoutTokenRequest;
import com.beratoztas.requests.RefreshTokenRequest;
import com.beratoztas.requests.RegisterRequest;
import com.beratoztas.responses.AuthResponse;

public interface IAuthenticationService {

	public AuthResponse register(RegisterRequest request);
	
	public AuthResponse login(LoginRequest request);
	
	public AuthResponse refresh(RefreshTokenRequest request);
	
	public void logout(LogoutTokenRequest request);
}
