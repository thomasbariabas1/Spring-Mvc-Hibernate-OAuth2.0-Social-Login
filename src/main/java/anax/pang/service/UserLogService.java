package anax.pang.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import anax.pang.model.Event;
import anax.pang.model.User;
import anax.pang.model.UserLog;
import anax.pang.model.UserStats;
import anax.pang.repository.UserLogRepository;
import anax.pang.repository.UserStatsRepository;

@Service
public class UserLogService {

	@Autowired
	private UserLogRepository userLogRepository;
	@Autowired
	private UserStatsRepository userStatsRepository;
	
	public UserLogRepository getUserLogRepository() {
		return userLogRepository;
	}
	
	public List<UserLog> getUserLogs(String email, Integer pageIndex, String sort, Integer limit) {
		return getUserLogRepository().getUserLogs(email, pageIndex, sort, limit);
	}
	
	public List<UserLog> getUsersLogs(Integer pageIndex, String sort, Integer limit) {
		return getUserLogRepository().getUserLogs(null, pageIndex, sort, limit);
	}
	//--------------------------------Thomas---------------------------------------------------------------
	public List<UserLog> getAppLogPerAppPerTimeGap(int AppId,Date Start_DateTime,Date End_DateTime){
		return userLogRepository.getAppLogPerAppPerTimeGap(AppId, Start_DateTime, End_DateTime);
		
	}
	
	public List<User> getAppLogPerAppPerActiveUser(int AppId){
		List<UserLog> list = userLogRepository.getAppLogPerAppPerActiveUser(AppId);
		List<UserLog> tmp = new ArrayList<UserLog>();
		List<User> users = new ArrayList<User>();
		HashMap<User,List<UserLog>> map = new HashMap<User, List<UserLog>>();
			   System.out.println(list);
		for(UserLog log:list){
			
			        List<UserLog> user = map.get(log.getUser());
			        if (user == null) {
			        	map.put(log.getUser(), user = new ArrayList<UserLog>());
			        }
			        user.add(log);          
			    
		}
		
		System.out.println(map);
		 Iterator it = map.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        tmp = map.get(pair.getKey());
				if(tmp.get(1).getEventDate().before(tmp.get(0).getEventDate()))
					users.add(tmp.get(1).getUser());
		    }
		
		
		return users;
	}
	public double getAppLogPerUserPerTotalTime(int AppId){
		long totalapptime =0;
		List<UserLog> list = userLogRepository.getAppLogPerUserPerTotalTime(AppId);
		for(int i=1; i<list.size();i+=2){
			totalapptime+= list.get(i).getEventDate().getTime() - list.get(i-1).getEventDate().getTime();
		}
		
		return (double)totalapptime/1000;
	}
    public long getAppLogPerUserPerTotalPoints(int AppId){
    	long totalapppoints =0;
    	List<UserStats>  list= userStatsRepository.getAppLogPerUserPerTotalPoints(AppId);
    	for(int i=0; i<list.size();i++){
    		totalapppoints+= list.get(i).getRewardPoints() ;
		}
		return  totalapppoints;
	}
	//--------------------------------Thomas---------------------------------------------------------------
}
