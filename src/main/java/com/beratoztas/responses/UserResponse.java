package com.beratoztas.responses;

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

	private String password;
	
	private UserRole userRole;

	public UserResponse(User user) {
		this.setId(user.getId());
		this.setCreatedTime(user.getCreatedTime());
		this.username =user.getUsername();
		this.userRole =user.getUserRole();
	}
	
	
}
