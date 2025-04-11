package com.beratoztas.requests;

import com.beratoztas.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	@NotEmpty
	@Size(min = 3 , max = 20)
	private String username;
	
	@NotEmpty
	@Size(min = 6,message = "Password must be at least 6 characters")
	private String password;
	
	@NotNull
	private UserRole userRole;
	
	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;
	
	@NotEmpty
	@Email
	private String email;
	
	
}
