package com.beratoztas.controller.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Management", description = "User management")
@RestController
@RequestMapping("/users")
public class RestUserControllerImpl extends RestBaseController implements IRestUserController {

	private IUserService userService;

	public RestUserControllerImpl(IUserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Get the current user's details", description = "Retrieves the details of the currently authenticated user.")
	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<UserResponse> getMe(@AuthenticationPrincipal JwtUserDetails userDetails) {
		return ok(userService.getUserById(userDetails.getId()));
	}

	@Operation(summary = "Get user by ID", description = "Retrieves the details of a user by their ID. Only accessible by users with 'ADMIN' role.")
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<UserResponse> getUserById(@PathVariable(name = "id") Long id) {
		return ok(userService.getUserById(id));
	}

	@Operation(summary = "Get all users (paginated)", description = "Returns a paginated list of all users. Only accessible by users with 'ADMIN' role.")
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<PageResponse<UserResponse>> getAllUsers(@ModelAttribute RestPageRequest request) {
		return ok(userService.getAllUsers(request));
	}

	@Operation(summary = "Update current user's details", description = "Updates the details of the currently authenticated user.")
	@PutMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<UserResponse> updateMe(@AuthenticationPrincipal JwtUserDetails userDetails,
			@RequestBody UpdateUserRequest request) {
		return ok(userService.updateUserById(userDetails.getId(), request));
	}

	@Operation(summary = "Update user by ID", description = "Updates the details of a user by their ID. Only accessible by users with 'ADMIN' role.")
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<UserResponse> updateUserById(@PathVariable(name = "id") Long id,
			@RequestBody UpdateUserRequest request) {
		return ok(userService.updateUserById(id, request));
	}

	@Operation(summary = "Delete current user's account", description = "Deletes the account of the currently authenticated user.")
	@DeleteMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<?> deleteMe(@AuthenticationPrincipal JwtUserDetails userDetails) {
		userService.deleteUserById(userDetails.getId());
		return ApiResponse.ok("Your account has been deleted");
	}

	@Operation(summary = "Delete user by ID", description = "Deletes a user by their ID. Only accessible by users with 'ADMIN' role.")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "BearerAuth")
	@Override
	public ApiResponse<?> deleteUserById(@PathVariable(name = "id") Long id) {
		userService.deleteUserById(id);
		return ApiResponse.ok("Your account has been deleted");
	}

}
