package anax.pang.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import anax.pang.model.User;
import anax.pang.model.UserLog;
import anax.pang.service.UserLogService;
import anax.pang.service.UserService;

@RestController
@RequestMapping("/api")
public class UserLogRestController {

	@Autowired
	private UserLogService userLogService;

	@Autowired
	private UserService userService;

	// Get UserStats ("userId" Long pathparam)
	@GetMapping("/user0/GetUserLogs")
	public ResponseEntity<List<?>> getUserLogs(	@RequestParam(value = "pageIndex", required = false) Integer pageIndex, 
												@RequestParam(value = "sort", required = false) String sort, 
												@RequestParam(value = "limit", required = false) Integer limit) {
		List<UserLog> userLogs = userLogService.getUserLogs(SecurityContextHolder.getContext().getAuthentication().getName(),
															pageIndex, 
															sort, 
															limit);
		for ( UserLog ul : userLogs ) {
			User user = new User();
			user.setEmail(ul.getUser().getEmail());
			ul.setUser(user);
		}
		
		return new ResponseEntity<List<?>>(userLogs, HttpStatus.OK);
	}
	
	@GetMapping("/GetUsersLogs")
	public ResponseEntity<List<?>> getUsersLogs(@RequestParam(value = "page", required = false) Integer page, 
												@RequestParam(value = "sort", required = false) String sort, 
												@RequestParam(value = "limit", required = false) Integer limit) {
		List<UserLog> userLogs = userLogService.getUsersLogs(page, sort, limit);
		
		for ( UserLog ul : userLogs ) {
			User user = new User();
			user.setEmail(ul.getUser().getEmail());
			ul.setUser(user);
		}
		
		return new ResponseEntity<List<?>>(userLogs, HttpStatus.OK);
	}
	
	

}
