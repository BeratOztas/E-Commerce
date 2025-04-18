package com.beratoztas.controller.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beratoztas.controller.ApiResponse;
import com.beratoztas.controller.IRestUserController;
import com.beratoztas.controller.RestBaseController;
import com.beratoztas.requests.RestPageRequest;
import com.beratoztas.requests.UpdateUserRequest;
import com.beratoztas.responses.PageResponse;
import com.beratoztas.responses.UserResponse;
import com.beratoztas.security.JwtUserDetails;
import com.beratoztas.service.IUserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/users")
public class RestUserControllerImpl extends RestBaseController implements IRestUserController {

	private IUserService userService;

	public RestUserControllerImpl(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<UserResponse> getMe(@AuthenticationPrincipal JwtUserDetails userDetails) {
		return ok(userService.getUserById(userDetails.getId()));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<UserResponse> getUserById(@PathVariable(name = "id") Long id) {
		return ok(userService.getUserById(id));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<PageResponse<UserResponse>> getAllUsers(RestPageRequest request) {
		return ok(userService.getAllUsers(request));
	}

	@PutMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<UserResponse> updateMe(@AuthenticationPrincipal JwtUserDetails userDetails,
			@RequestBody UpdateUserRequest request) {
		return ok(userService.updateUserById(userDetails.getId(), request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<UserResponse> updateUserById(@PathVariable(name = "id") Long id,
			@RequestBody UpdateUserRequest request) {
		return ok(userService.updateUserById(id, request));
	}

	@DeleteMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<?> deleteMe(@AuthenticationPrincipal JwtUserDetails userDetails) {
		userService.deleteUserById(userDetails.getId());
		return ApiResponse.ok("Your account has been deleted");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<?> deleteUserById(@PathVariable(name = "id") Long id) {
		userService.deleteUserById(id);
		return ApiResponse.ok("Your account has been deleted");
	}

}
