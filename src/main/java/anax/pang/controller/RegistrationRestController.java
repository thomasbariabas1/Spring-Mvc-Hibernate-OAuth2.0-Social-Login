package anax.pang.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import anax.pang.model.User;
import anax.pang.service.RegistryTypeService;
import anax.pang.service.RoleService;
import anax.pang.service.UserService;
import anax.pang.utility.HashUtilityBean;

@RestController
@RequestMapping("/register")
public class RegistrationRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private RegistryTypeService registryTypeService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private HashUtilityBean hashUtilityBean;

	@PostMapping("")
	@ResponseStatus(value = HttpStatus.CREATED)
	public User createUser(@Valid @RequestBody User user) {
		user.setRegistryType(registryTypeService.getRegistryType("regcust"));
		user.setRole(roleService.getRole("ROLE_USER"));
		user.setPassword(hashUtilityBean.encryptHashMD5(user.getPassword()));

		return userService.createUser(user);
	}
}
