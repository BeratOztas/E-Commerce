package com.beratoztas.controllerimpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beratoztas.controller.ApiResponse;
import com.beratoztas.controller.IRestAuthenticationController;
import com.beratoztas.controller.RestBaseController;
import com.beratoztas.requests.LoginRequest;
import com.beratoztas.requests.RefreshTokenRequest;
import com.beratoztas.requests.RegisterRequest;
import com.beratoztas.responses.AuthResponse;
import com.beratoztas.responses.UserResponse;
import com.beratoztas.service.IAuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class RestAuthenticationControllerImpl extends RestBaseController  implements IRestAuthenticationController{
	
	private IAuthenticationService authenticationService;
	
	public RestAuthenticationControllerImpl(IAuthenticationService authenticationService) {
		this.authenticationService =authenticationService;
	}

	@Override
	@PostMapping("/register")
	public ApiResponse<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
		return ok(authenticationService.register(request));
	}

	@Override
	@PostMapping("/login")
	public ApiResponse<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
		return ok(authenticationService.login(request));
	}

	@Override
	@PostMapping("/refresh")
	public ApiResponse<AuthResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
		return ok(authenticationService.refresh(request));
	}

	@Override
	@PostMapping("/logout")
	public ApiResponse<?> logout(@RequestBody RefreshTokenRequest request) {
		authenticationService.logout(request);
		return ApiResponse.ok("Logged out!");
	}

}
