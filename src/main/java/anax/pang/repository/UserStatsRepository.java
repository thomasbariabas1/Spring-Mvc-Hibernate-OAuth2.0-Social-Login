package anax.pang.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import anax.pang.exception.DataAlreadyExistsException;
import anax.pang.exception.DataNotFoundException;
import anax.pang.exception.UnbeatenPreviousLevelException;
import anax.pang.model.App;
import anax.pang.model.User;
import anax.pang.model.UserLog;
import anax.pang.model.UserStats;

@Transactional
@Repository
public class UserStatsRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session currentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public UserStats getUserStatsByUserIdAppIdLevel(User user, Integer appId, Integer level) {
		UserStats userStats = null;
		try {
			userStats = currentSession().createNamedQuery("UserStats.findByUserIdAppIdLevel", UserStats.class)
						.setParameter("user", user)
						.setParameter("appId", appId)
						.setParameter("level", level)
						.getSingleResult();
		} catch ( NoResultException e ) {
			throw new DataNotFoundException("Stats not found.");
		}
		
		return userStats;
	}
	
	public UserStats getUserStats(User user, Integer appId, Integer level) {
		UserStats userStats = null;
		try {
			userStats = currentSession().createNamedQuery("UserStats.findByUserIdAppIdLevel", UserStats.class)
						.setParameter("user", user)
						.setParameter("appId", appId)
						.setParameter("level", level)
						.getSingleResult();
		} catch ( NoResultException e ) {
			return null;
		}
		
		return userStats;
	}
	
	public List<UserStats> getUserStatsByUserIdAppId(User user, Integer appId) {
		return currentSession().createNamedQuery("UserStats.findByUserIdAppId", UserStats.class)
					.setParameter("user", user)
					.setParameter("appId", appId)
					.getResultList();
	}
	
	public List<UserStats> getUserStatsByUserId(User user) {
		return currentSession().createNamedQuery("UserStats.findByUserId", UserStats.class)
					.setParameter("user", user)
					.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserStats> getTopUsers(	User user, 
										Integer howmany, 
										Integer pageIndex, 
										Boolean usr_middle_MODE,
										Integer appId,	
										Integer level) {
		List<UserStats> listOfUserStats = null;
		
		if ( usr_middle_MODE ) {
			listOfUserStats =  currentSession().getNamedProcedureCall("TopUsers")
													.setParameter("IN_UserId", user.getId())
													.setParameter("IN_HowMany", howmany)
													.setParameter("IN_AppId", appId)
													.setParameter("IN_AppLevel", level)
												.getResultList();
		} else {
			listOfUserStats = currentSession().createNamedQuery("UserStats.findAll.Top", UserStats.class)
														.setParameter("appId", appId)
														.setParameter("level", level)
														.setFirstResult(pageIndex.intValue() * howmany.intValue())
														.setMaxResults(howmany.intValue())
													.getResultList();
		}
		
		return retrieveLazyFields(listOfUserStats);
	}
	
	public UserStats updateUserStats(User user, Integer appId, Integer level, UserStats userStats) {
		UserStats getUserStats = null;
		try {
			getUserStats = (UserStats) currentSession().createQuery("FROM UserStats us WHERE us.user = :user AND us.app.appId = :appId AND us.level = :level")
														.setParameter("user", user)
														.setParameter("appId", appId)
														.setParameter("level", level)
														.getSingleResult();
		} catch ( NoResultException e ) {
			throw new DataNotFoundException("Stats not found.");
		}
		
		getUserStats.setLatestScore(userStats.getLatestScore());
		getUserStats.setDateLastScore(new Date());
		
		if ( getUserStats.getHighScore() < getUserStats.getLatestScore() ) {
			getUserStats.setHighScore(getUserStats.getLatestScore());
			getUserStats.setDateHighScore(new Date());
		}
		
		getUserStats = (UserStats) currentSession().merge(getUserStats);
		
		return (UserStats) getUserStatsByUserIdAppIdLevel(getUserStats.getUser(), getUserStats.getApp().getAppId(), getUserStats.getLevel());
	}
	
	public Long createUserStats(User user, App app, Integer level, UserStats userStats) {
		Integer levelIndex = level - 1;
		try {
			app.getListOfAppInfo().get(levelIndex);
		} catch ( IndexOutOfBoundsException e ) {
			throw new DataNotFoundException("Level not found");
		}

		UserStats searchStats = getUserStats(user, app.getAppId(), level);
		if ( searchStats != null ) {
			throw new DataAlreadyExistsException("Stats already exist");
		}
		
		if ( level == 1 ) {
			userStats = preSaveAction(user, app, level, userStats);
			return (Long) currentSession().save(userStats);
		} else {
			searchStats = getUserStatsByUserIdAppIdLevel(user, app.getAppId(), level - 1);
			Double previousScore = app.getListOfAppInfo().get(levelIndex - 1).getScore();
			
			if ( searchStats.getHighScore() >= previousScore) {
				userStats = preSaveAction(user, app, level, userStats);
				return (Long) currentSession().save(userStats);
			} else {
				throw new UnbeatenPreviousLevelException("Previous level unbeaten");
			}
		}
	}

	private List<UserStats> retrieveLazyFields(List<UserStats> listOfUserStats) {
		for ( UserStats us : listOfUserStats ) {
			us.getUser().getEmail();
		}
		
		return listOfUserStats;
	}
	
	private UserStats preSaveAction(User user, App app, Integer level, UserStats userStats) {
		userStats.setUser(user);
		userStats.setApp(app);
		userStats.setLevel(level);
		userStats.setHighScore(userStats.getLatestScore());
		
		return userStats;
	}
	@SuppressWarnings("unchecked")
	public List<UserStats> getAppLogPerUserPerTotalPoints(int AppId){
		TypedQuery<UserStats> query = null;
		query = currentSession().createQuery(" from UserStats us where us.app.app_id = :app_id  ").setParameter("app_id", AppId);
		
		return query.getResultList();
	}
}
