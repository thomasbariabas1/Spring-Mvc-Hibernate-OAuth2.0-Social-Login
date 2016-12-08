package anax.pang.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import anax.pang.model.*;
import anax.pang.service.UserService;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private UserService userService;

	// Get User Info ("userId" Long pathparam)
	@GetMapping("/user0/info")
	public ResponseEntity<User> getUserInfo() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUser(email);
		hideSensitiveData(user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// Update User Info ("userId" Long pathparam, consumes JSON)
	@PutMapping("/user0/info")
	public ResponseEntity<User> updateUserInfo(	@Valid @RequestBody User user) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		user.setEmail(email);
		user = userService.updateUserInfo(user);
		hideSensitiveData(user);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// Get Users
	@GetMapping("/users")
	public ResponseEntity<List<?>> getUsers() {
		return new ResponseEntity<List<?>>(userService.getUsers(), HttpStatus.OK);
	}

	// Validation and setter functions
	private void hideSensitiveData(User user) {
		user.setId(null);
		user.setEmail(null);
		user.setPassword(null);
		user.setRole(null);
		user.setRegistryType(null);
	}

}