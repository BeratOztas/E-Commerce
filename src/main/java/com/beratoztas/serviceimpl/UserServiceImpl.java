package com.beratoztas.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.beratoztas.entities.User;
import com.beratoztas.exception.BaseException;
import com.beratoztas.exception.ErrorMessage;
import com.beratoztas.exception.MessageType;
import com.beratoztas.repository.UserRepository;
import com.beratoztas.requests.UpdateUserRequest;
import com.beratoztas.responses.UserResponse;
import com.beratoztas.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private User findByUserId(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.USER_NOT_FOUND, "User Id : " + id)));
	}

	@Override
	public UserResponse getUserById(Long id) {
		User user = findByUserId(id);

		return new UserResponse(user);
	}

	@Override
	public List<UserResponse> getAllUsers() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.USERS_NOT_FOUND, ""));
		}
		return users.stream().map(user -> new UserResponse(user)).collect(Collectors.toList());
	}

	@Override
	public UserResponse updateUserById(Long id, UpdateUserRequest request) {
		User user = findByUserId(id);

		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());

		User savedUser = userRepository.save(user);
		return new UserResponse(savedUser);
	}

	@Override
	public void deleteUserById(Long id) {
		User user = findByUserId(id);

		userRepository.delete(user);

	}

}
