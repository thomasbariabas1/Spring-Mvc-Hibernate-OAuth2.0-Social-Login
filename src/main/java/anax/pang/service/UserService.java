package anax.pang.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import anax.pang.exception.DataAlreadyExistsException;
import anax.pang.exception.DataNotFoundException;
import anax.pang.model.User;
import anax.pang.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserRepository getUserRepository() {
		return this.userRepository;
	}

	public User getUser(Integer useId) {
		User user = getUserRepository().getUser(useId);
		if (user == null) {
			throwDataNotFoundException();
		}

		return user;
	}

	public User getUser(String email) {
		User user = getUserRepository().getUser(email);
		if (user == null) {
			throwDataNotFoundException();
		}

		return user;
	}

	public List<User> getUsers() {
		return getUserRepository().getUsers();
	}
	
	public User createUser(User user) {
		User searchUser = getUserRepository().getUser(user.getEmail());
		if (searchUser != null) {
			throwDataAlreadyExistsException();
		}
		
		user.setId(getUserRepository().createUser(user));
		return user;
	}

	public User updateUserInfo(User user) {
		User searchUser = getUserRepository().getUser(user.getEmail());
		if (searchUser == null) {
			throwDataNotFoundException();
		}
		
		searchUser.setFirstName(user.getFirstName());
		searchUser.setLastName(user.getLastName());
		searchUser.setAddress(user.getAddress());
		searchUser.setTelephone(user.getTelephone());

		return getUserRepository().updateUserInfo(user);
	}

	// Throw exception handlers
	private void throwDataNotFoundException() {
		DataNotFoundException exception = new DataNotFoundException("User not found.");
		throw exception;
	}

	private void throwDataAlreadyExistsException() {
		DataAlreadyExistsException exception = new DataAlreadyExistsException("User already exists.");
		throw exception;
	}

}
