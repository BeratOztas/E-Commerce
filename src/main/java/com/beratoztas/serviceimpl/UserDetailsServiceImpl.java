package com.beratoztas.serviceimpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.beratoztas.entities.User;
import com.beratoztas.repository.UserRepository;
import com.beratoztas.security.JwtUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user =userRepository.findByUsername(username);
		if(user ==null)
			return null;// Throw USER_NOT_FOUND Exception
		return JwtUserDetails.create(user);
	}
	
	public UserDetails loadUserById(Long id) {
		User user =userRepository.findById(id).get();
		if(user ==null)
			return null; // THROW USER_NOT_FOUND EXCEPTION
		return JwtUserDetails.create(user);
	}
	
}
