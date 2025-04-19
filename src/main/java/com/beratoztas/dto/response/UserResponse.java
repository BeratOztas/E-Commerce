package com.beratoztas.dto.response;

import com.beratoztas.entities.User;
import com.beratoztas.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends BaseDto {

	private String username;

	private String firstName;

	private String lastName;

	private String email;

	private UserRole userRole;

	public UserResponse(User user) {
		this.setId(user.getId());
		this.setCreatedTime(user.getCreatedTime());
		this.username =user.getUsername();
		this.firstName =user.getFirstName();
		this.lastName =user.getLastName();
		this.email =user.getEmail();
		this.userRole =user.getUserRole();
	}

}
