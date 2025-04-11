package com.beratoztas.controllerimpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beratoztas.controller.IRestAuthenticationController;
import com.beratoztas.requests.LoginRequest;
import com.beratoztas.requests.RefreshTokenRequest;
import com.beratoztas.requests.RegisterRequest;
import com.beratoztas.responses.AuthResponse;
import com.beratoztas.responses.UserResponse;
import com.beratoztas.service.IAuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class IRestAuthenticationControllerImpl  implements IRestAuthenticationController{
	
	private IAuthenticationService authenticationService;
	
	public IRestAuthenticationControllerImpl(IAuthenticationService authenticationService) {
		this.authenticationService =authenticationService;
	}

	@Override
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
		return authenticationService.register(request);
	}

	@Override
	@PostMapping("/login")
	public AuthResponse login(@RequestBody @Valid LoginRequest request) {
		return authenticationService.login(request);
	}

	@Override
	@PostMapping("/refresh")
	public AuthResponse refresh(@RequestBody @Valid RefreshTokenRequest request) {
		return authenticationService.refresh(request);
	}

	@Override
	@PostMapping("/logout")
	public void logout(@RequestBody RefreshTokenRequest request) {
		
	}

}
