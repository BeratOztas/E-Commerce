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
import com.beratoztas.requests.LoginRequest;
import com.beratoztas.requests.LogoutTokenRequest;
import com.beratoztas.requests.RefreshTokenRequest;
import com.beratoztas.requests.RegisterRequest;
import com.beratoztas.responses.AuthResponse;
import com.beratoztas.service.IAuthenticationService;
import com.beratoztas.utils.CookieUtil;

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
	public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody @Valid RegisterRequest request) {
		AuthResponse response =authenticationService.register(request);
		ResponseCookie accessTokenCookie =CookieUtil.createAccessTokenCookie(response.getAccessToken());
		
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
				.body(ApiResponse.ok(response));
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest request) {
		AuthResponse response =authenticationService.login(request);
		ResponseCookie accessTokenCookie =CookieUtil.createAccessTokenCookie(response.getAccessToken());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
				.body(ApiResponse.ok(response));
	}

	@Override
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestBody @Valid RefreshTokenRequest request) {
		AuthResponse response =authenticationService.refresh(request);
		ResponseCookie accessTokenCookie =CookieUtil.createAccessTokenCookie(response.getAccessToken());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
				.body(ApiResponse.ok(response));
	}

	@Override
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<?>> logout(@RequestBody LogoutTokenRequest request) {
		authenticationService.logout(request);
		ResponseCookie clearedCookie =CookieUtil.clearAccessTokenCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, clearedCookie.toString())
				.body(ApiResponse.ok("Logged out succesfully.!"));
	}

}
