package com.beratoztas.serviceimpl;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.beratoztas.entities.User;
import com.beratoztas.exception.BaseException;
import com.beratoztas.exception.ErrorMessage;
import com.beratoztas.exception.MessageType;
import com.beratoztas.repository.UserRepository;
import com.beratoztas.requests.LoginRequest;
import com.beratoztas.requests.RefreshTokenRequest;
import com.beratoztas.requests.RegisterRequest;
import com.beratoztas.responses.AuthResponse;
import com.beratoztas.security.JwtTokenProvider;
import com.beratoztas.security.JwtUserDetails;
import com.beratoztas.service.IAuthenticationService;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

	private UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder;

	private JwtTokenProvider jwtTokenProvider;

	private AuthenticationManager authenticationManager;

	public AuthenticationServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
	}

	private User createUser(RegisterRequest request) {
		User user = new User();

		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setUserRole(request.getUserRole());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());

		return user;
	}

	@Override
	public ResponseEntity<AuthResponse> register(RegisterRequest request) {
		if (userRepository.findByUsername(request.getUsername()) != null) {
			throw new BaseException(new ErrorMessage(MessageType.USERNAME_ALREADY_EXIST, request.getUsername()));
		}
		if (userRepository.findByEmail(request.getEmail()) != null) {
			throw new BaseException(new ErrorMessage(MessageType.EMAIL_ALREADY_EXIST, request.getEmail()));
		}
		AuthResponse authResponse = new AuthResponse();

		User savedUser = userRepository.save(createUser(request));

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUsername(),
				request.getPassword());

		Authentication auth = authenticationManager.authenticate(authToken);

		SecurityContextHolder.getContext().setAuthentication(auth);

		JwtUserDetails userDetails = JwtUserDetails.create(savedUser);

		String jwtToken = jwtTokenProvider.generateJwtToken(userDetails);

		authResponse.setMessage("User registered succesfully!!.");
		authResponse.setUserId(savedUser.getId());
		authResponse.setAccessToken(jwtToken);

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		try {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					request.getUsername(), request.getPassword());

			Authentication auth = authenticationManager.authenticate(authToken);

			SecurityContextHolder.getContext().setAuthentication(auth);

			User user = userRepository.findByUsername(request.getUsername());

			JwtUserDetails userDetails = JwtUserDetails.create(user);

			String jwtToken = jwtTokenProvider.generateJwtToken(userDetails);

			AuthResponse authResponse = new AuthResponse();

			authResponse.setMessage("User logined succesfully !!.");
			authResponse.setUserId(user.getId());
			authResponse.setAccessToken("Bearer " + jwtToken);

			return authResponse;

		} catch (Exception e) {
			// TODO: THROW USERNAME_OR_PASSWORD_INVALID EXCEPTION
			throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
		}
	}

	@Override
	public AuthResponse refresh(RefreshTokenRequest request) {
		return new AuthResponse("Token succesfully refreshed", Integer.toUnsignedLong(4), "newAccessToken_example123",
				"dummyRefreshToken");
	}

	@Override
	public void logout(RefreshTokenRequest request) {
		// Redis bağlantısı
		System.out.println("Log out çalıştı. Token : " + request.getRefreshToken());
	}

}
