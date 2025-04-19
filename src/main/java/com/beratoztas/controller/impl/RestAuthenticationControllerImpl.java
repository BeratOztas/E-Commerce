package com.beratoztas.controller.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beratoztas.controller.ApiResponse;
import com.beratoztas.controller.IRestAuthenticationController;
import com.beratoztas.controller.RestBaseController;
import com.beratoztas.dto.request.LoginRequest;
import com.beratoztas.dto.request.LogoutTokenRequest;
import com.beratoztas.dto.request.RefreshTokenRequest;
import com.beratoztas.dto.request.RegisterRequest;
import com.beratoztas.dto.response.AuthResponse;
import com.beratoztas.service.IAuthenticationService;
import com.beratoztas.utils.CookieUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "Handles user registration, login, logout, and refresh token")
@RestController
@RequestMapping("/auth")
public class RestAuthenticationControllerImpl extends RestBaseController  implements IRestAuthenticationController{

	private IAuthenticationService authenticationService;

	public RestAuthenticationControllerImpl(IAuthenticationService authenticationService) {
		this.authenticationService =authenticationService;
	}

	@Operation(summary = "Register new user", description = "Registers a user and returns JWT access and refresh tokens in cookie and body.")
	@Override
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody @Valid RegisterRequest request) {
		AuthResponse response =authenticationService.register(request);
		ResponseCookie accessTokenCookie =CookieUtil.createAccessTokenCookie(response.getAccessToken());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
				.body(ApiResponse.ok(response));
	}

	@Operation(summary = "Login user", description = "Authenticates a user and returns access and refresh tokens.")
	@Override
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest request) {
		AuthResponse response =authenticationService.login(request);
		ResponseCookie accessTokenCookie =CookieUtil.createAccessTokenCookie(response.getAccessToken());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
				.body(ApiResponse.ok(response));
	}

	 @Operation(summary = "Refresh access token", description = "Creates new access and refresh token using the provided refresh token.")
	@Override
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestBody @Valid RefreshTokenRequest request) {
		AuthResponse response =authenticationService.refresh(request);
		ResponseCookie accessTokenCookie =CookieUtil.createAccessTokenCookie(response.getAccessToken());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
				.body(ApiResponse.ok(response));
	}

	@Operation(summary = "Logout user", description = "Deletes refresh token from Redis and clears access token cookie.")
	@Override
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<?>> logout(@RequestBody LogoutTokenRequest request) {
		authenticationService.logout(request);
		ResponseCookie clearedCookie =CookieUtil.clearAccessTokenCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, clearedCookie.toString())
				.body(ApiResponse.ok("Logged out succesfully.!"));
	}

}
