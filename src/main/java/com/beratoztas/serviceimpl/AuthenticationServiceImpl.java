package com.beratoztas.serviceimpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beratoztas.entities.Address;
import com.beratoztas.entities.User;
import com.beratoztas.enums.UserRole;
import com.beratoztas.exception.BaseException;
import com.beratoztas.exception.ErrorMessage;
import com.beratoztas.exception.MessageType;
import com.beratoztas.repository.AddressRepository;
import com.beratoztas.repository.UserRepository;
import com.beratoztas.requests.LoginRequest;
import com.beratoztas.requests.LogoutTokenRequest;
import com.beratoztas.requests.RefreshTokenRequest;
import com.beratoztas.requests.RegisterRequest;
import com.beratoztas.responses.AuthResponse;
import com.beratoztas.security.JwtTokenProvider;
import com.beratoztas.security.JwtUserDetails;
import com.beratoztas.service.IAuthenticationService;

import io.micrometer.common.util.StringUtils;

@Service
@Transactional
public class AuthenticationServiceImpl implements IAuthenticationService {

	private UserRepository userRepository;

	private AddressRepository addressRepository;

	private BCryptPasswordEncoder passwordEncoder;

	private JwtTokenProvider jwtTokenProvider;

	private AuthenticationManager authenticationManager;

	private RefreshTokenServiceImpl refreshTokenService;

	public AuthenticationServiceImpl(UserRepository userRepository, AddressRepository addressRepository,
			BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
			RefreshTokenServiceImpl refreshTokenService, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
		this.refreshTokenService = refreshTokenService;
	}

	@Override
	public AuthResponse register(RegisterRequest request) {
		if (userRepository.findByUsername(request.getUsername()) != null) {
			throw new BaseException(new ErrorMessage(MessageType.USERNAME_ALREADY_EXIST, request.getUsername()));
		}
		if (userRepository.findByEmail(request.getEmail()) != null) {
			throw new BaseException(new ErrorMessage(MessageType.EMAIL_ALREADY_EXIST, request.getEmail()));
		}

		User savedUser = userRepository.save(createUser(request));

		if (savedUser.getUserRole() == UserRole.USER && request.getCity() != null)
			saveAddress(savedUser, request);

		authenticateUser(request.getUsername(), request.getPassword());

		JwtUserDetails userDetails = JwtUserDetails.create(savedUser);

		String accessToken = jwtTokenProvider.generateJwtToken(userDetails);
		String refreshToken = refreshTokenService.generateRefreshToken();

		refreshTokenService.saveRefreshToken(savedUser.getId(), refreshToken);

		return buildAuthResponse(savedUser.getId(), accessToken, refreshToken, "User registered successfully!");

	}

	@Override
	public AuthResponse login(LoginRequest request) {
		try {
			authenticateUser(request.getUsername(), request.getPassword());

			User user = userRepository.findByUsername(request.getUsername());

			JwtUserDetails userDetails = JwtUserDetails.create(user);

			String accessToken = jwtTokenProvider.generateJwtToken(userDetails);
			String refreshToken = refreshTokenService.generateRefreshToken();

			refreshTokenService.saveRefreshToken(user.getId(), refreshToken);

			return buildAuthResponse(user.getId(), accessToken, refreshToken, "User logged in successfully!");

		} catch (Exception e) {
			throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
		}
	}

	@Override
	public AuthResponse refresh(RefreshTokenRequest request) {
		String oldRefreshToken = request.getRefreshToken();

		if (!refreshTokenService.isValidRefreshToken(oldRefreshToken)) {
			throw new BaseException(
					new ErrorMessage(MessageType.INVALID_REFRESH_TOKEN, "Refresh token is not valid or expired."));
		}

		Long userId = refreshTokenService.getUserIdFromRefreshToken(oldRefreshToken);
		User user = userRepository.findById(userId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.USER_NOT_FOUND, "User Id : " + userId)));

		refreshTokenService.deleteRefreshToken(oldRefreshToken);

		JwtUserDetails userDetails = JwtUserDetails.create(user);

		String accessToken = jwtTokenProvider.generateJwtToken(userDetails);
		String newRefreshToken = refreshTokenService.generateRefreshToken();

		refreshTokenService.saveRefreshToken(userId, newRefreshToken);

		return buildAuthResponse(user.getId(), accessToken, newRefreshToken, "Token refreshed successfully!");
	}

	@Override
	public void logout(LogoutTokenRequest request) {
		String refreshToken = request.getRefreshToken();

		if (StringUtils.isBlank(refreshToken)) {
			throw new BaseException(new ErrorMessage(MessageType.INVALID_REFRESH_TOKEN, "Refresh token is missing."));
		}

		refreshTokenService.deleteRefreshToken(refreshToken);
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
	
	private void saveAddress(User savedUser, RegisterRequest request) {
		Address address = new Address();

		address.setUser(savedUser);
		address.setCity(request.getCity());
		address.setDistrict(request.getDistrict());
		address.setNeighborhood(request.getNeighborhood());
		address.setStreet(request.getStreet());

		addressRepository.save(address);
	}
	
	private void authenticateUser(String username, String password) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private AuthResponse buildAuthResponse(Long userId, String accessToken, String refreshToken, String message) {
		AuthResponse response = new AuthResponse();
		
		response.setUserId(userId);
		response.setMessage(message);
		response.setAccessToken(accessToken);
		response.setRefreshToken(refreshToken);
		
		return response;
	}
	
}
