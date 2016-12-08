package anax.pang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import anax.pang.model.App;
import anax.pang.model.User;
import anax.pang.model.UserStats;
import anax.pang.repository.UserStatsRepository;

@Service
public class UserStatsService {

	@Autowired
    private UserStatsRepository userStatsRepository;
	
	public UserStats getUserStatsByUserIdAppIdLevel(User user, Integer appId, Integer level) {
		return userStatsRepository.getUserStatsByUserIdAppIdLevel(user, appId, level);
	}
	
	public UserStats getUserStats(User user, Integer appId, Integer level) {
		return userStatsRepository.getUserStats(user, appId, level);
	}
	
	public List<UserStats> getUserStatsByUserIdAppId(User user, Integer appId) {
		return userStatsRepository.getUserStatsByUserIdAppId(user, appId);
	}
	
	public List<UserStats> getUserStatsByUserId(User user) {
		return userStatsRepository.getUserStatsByUserId(user);
	}
		
	public List<UserStats> getTopUsers(	User user, 
										Integer howmany, 
										Integer pageIndex, 
										Boolean usr_middle_MODE, 
										Integer appId,	
										Integer level) {
		List<UserStats> listOfUserStats = userStatsRepository.getTopUsers(user, howmany, pageIndex, usr_middle_MODE, appId, level); 
		
		hideSensitiveData(listOfUserStats);
		
		return listOfUserStats;
	}
	
	public UserStats updateUserStats(User user, Integer appId, Integer level, UserStats userStats) {
		return userStatsRepository.updateUserStats(user, appId, level, userStats);
	}
	
	public UserStats createUserStats(User user, App app, Integer level, UserStats userStats) {
		userStats.setUserStatsId(userStatsRepository.createUserStats(user, app, level, userStats));
		return hideSensitiveDataObject(userStats);
	}

	private void hideSensitiveData(List<UserStats> listOfUserStats) {
		for ( UserStats us : listOfUserStats ) {
			hideSensitiveDataObject(us);
		}
	}

	private UserStats hideSensitiveDataObject(UserStats us) {
		us.setUserStatsId(null);
		us.setApp(null);
		us.setUser(new User(us.getUser().getEmail()));
		
		return us;
	}
}
