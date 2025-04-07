package com.beratoztas.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.beratoztas.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

	@Column(name = "username",unique = true ,nullable = false)
	private String username;

	@Column(name = "password" ,nullable = false)
	private String password;

	@Column(name = "user_role" )
	@Enumerated(EnumType.STRING)
	private UserRole userRole =UserRole.USER;

	@Column(name = "first_name" ,nullable = false)
	private String firstName;

	@Column(name = "last_name" ,nullable = false)
	private String lastName;

	@Column(name = "email" ,nullable = false ,unique = true)
	private String email;
	
	@OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
	private List<Order> orders;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(userRole.name()));
	}
}
