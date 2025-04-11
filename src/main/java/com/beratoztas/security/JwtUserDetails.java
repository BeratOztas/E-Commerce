package com.beratoztas.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.beratoztas.entities.User;
import com.beratoztas.enums.UserRole;

import lombok.Data;

@Data
public class JwtUserDetails implements UserDetails {

	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtUserDetails(Long id, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static JwtUserDetails create(User user) {
		List<GrantedAuthority> authoritiesList = new ArrayList<>();
		if (user.getUserRole() == UserRole.ADMIN) {
			authoritiesList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			authoritiesList.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return new JwtUserDetails(user.getId(), user.getUsername(), user.getPassword(), authoritiesList);
	}


}
