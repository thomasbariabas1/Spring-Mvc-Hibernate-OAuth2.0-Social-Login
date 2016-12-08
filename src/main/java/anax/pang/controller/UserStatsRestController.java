package anax.pang.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import anax.pang.model.App;
import anax.pang.model.User;
import anax.pang.model.UserStats;
import anax.pang.service.AppService;
import anax.pang.service.UserService;
import anax.pang.service.UserStatsService;

@RestController
@RequestMapping("/api/user0/userstats")
public class UserStatsRestController {

	private final String const_howmany_Str = "40";
	private final String const_pageIndex_Str = "0";
	private final String const_usr_middle_MODE = "true";
	
	@Autowired
	private UserStatsService userStatsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AppService appService;
	
	@GetMapping("")
	public List<?> getUserStats(	@RequestHeader(value = "appId", required = false)  Integer appId,	
									@RequestHeader(value = "level", required = false)  Integer level ) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if ( (appId != null) && (level != null) ) {
			return Lists.newArrayList(userStatsService.getUserStatsByUserIdAppIdLevel(user, appId, level));
		} else if (appId != null) {
			return userStatsService.getUserStatsByUserIdAppId(user, appId);
		} else {
			return userStatsService.getUserStatsByUserId(user);
		}
	}
	
	@GetMapping("/top")
	public List<?> getTopUsers(	@RequestParam(required = false, value = "howmany", defaultValue = const_howmany_Str) Integer howmany,
								@RequestParam(required = false, value = "pageIndex", defaultValue = const_pageIndex_Str) Integer pageIndex,
								@RequestParam(required = false, value = "usr_middle_MODE", defaultValue = const_usr_middle_MODE) Boolean usr_middle_MODE,
								@RequestHeader(value = "appId", required = true)  Integer appId,	
								@RequestHeader(value = "level", required = true)  Integer level) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		checkVar(howmany);
		checkVar(pageIndex);
		
		return userStatsService.getTopUsers(user, howmany, pageIndex, usr_middle_MODE, appId, level);
	}
	
	@PutMapping("")
	public UserStats updateUserStats(	@RequestHeader(value = "appId", required = true) Integer appId,
										@RequestHeader(value = "level", required = true) Integer level,
										@Valid @RequestBody UserStats userStats) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());

		return userStatsService.updateUserStats(user, appId, level, userStats);
	}
	
	@PostMapping("")
	public UserStats createUserStats(	@RequestHeader(value = "appId", required = true)  Integer appId,
										@RequestHeader(value = "level", required = true)  Integer level,
										@Valid @RequestBody UserStats userStats) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		App app = appService.getAppByAppId(appId, true);
		
		return userStatsService.createUserStats(user, app, level, userStats);
	}
	
	// Validation and exception functions
	private Integer checkVar(Integer var) {
		if ( var < 0 ) {
			var = 0;
		}
		
		return var;
	}
}
