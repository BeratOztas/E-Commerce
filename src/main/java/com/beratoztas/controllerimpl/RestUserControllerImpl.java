package com.beratoztas.controllerimpl;

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

import com.beratoztas.controller.IRestUserController;
import com.beratoztas.requests.UpdateUserRequest;
import com.beratoztas.responses.UserResponse;
import com.beratoztas.security.JwtUserDetails;
import com.beratoztas.service.IUserService;

@RestController
@RequestMapping("/users")
public class RestUserControllerImpl implements IRestUserController {

	private IUserService userService;
	
	public RestUserControllerImpl(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@Override
	public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal JwtUserDetails userDetails) {
		return ResponseEntity.ok(userService.getUserById(userDetails.getId()));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PutMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@Override
	public ResponseEntity<UserResponse> updateMe(@AuthenticationPrincipal JwtUserDetails userDetails,@RequestBody UpdateUserRequest request) {
		return ResponseEntity.ok(userService.updateUserById(userDetails.getId(), request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<UserResponse> updateUserById(@PathVariable(name = "id") Long id,@RequestBody UpdateUserRequest request) {
		return ResponseEntity.ok(userService.updateUserById(id, request));
	}

	@DeleteMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@Override
	public ResponseEntity<?> deleteMe(@AuthenticationPrincipal JwtUserDetails userDetails) {
		userService.deleteUserById(userDetails.getId());
		return ResponseEntity.ok("Your account has been deleted");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<?> deleteUserById(@PathVariable(name = "id") Long id) {
		userService.deleteUserById(id);
		return ResponseEntity.ok("Your account has been deleted");
	}

}
