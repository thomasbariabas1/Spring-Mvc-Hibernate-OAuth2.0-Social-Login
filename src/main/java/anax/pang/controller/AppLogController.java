package anax.pang.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import anax.pang.service.UserLogService;
import anax.pang.service.UserStatsService;

@RestController
@RequestMapping("/api/user0/apps/log")
public class AppLogController {

	@Autowired
	private UserLogService userLogService;
	
	@GetMapping("/{appId}")
	public  int getApp(	@PathVariable Integer appId, 
						@RequestParam("start_datetime")  String Start_DateTime,@RequestParam("end_datetime")  String End_DateTime) throws ParseException {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = format.parse(Start_DateTime);

		
		Date end = format.parse(End_DateTime);
		
		return userLogService.getAppLogPerAppPerTimeGap(appId, start, end).size();
	}
	@GetMapping("/{appId}/connected")
	public  int getAppPerConnected(	@PathVariable Integer appId
						) throws ParseException {
		
		
		
		return userLogService.getAppLogPerAppPerActiveUser(appId).size();
	}
	@GetMapping("/{appId}/totaltime")
	public  double getAppPerTotalTime(	@PathVariable Integer appId
						) throws ParseException {
		
		
		
		return userLogService.getAppLogPerUserPerTotalTime(appId);
	}
	@GetMapping("/{appId}/totalpoints")
	public  long getAppPerTotalPoints(	@PathVariable Integer appId
						) throws ParseException {
		
		
		
		return userLogService.getAppLogPerUserPerTotalPoints(appId);
	}
}
