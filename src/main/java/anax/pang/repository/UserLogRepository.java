package anax.pang.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import anax.pang.model.UserLog;

@Transactional
@Repository
public class UserLogRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session currentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public UserLog getUserLog(Long userLogId) {
		return currentSession().get(UserLog.class, userLogId);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserLog> getUserLogs(String email, Integer page, String sort, Integer limit) {
		String queryStr = "select ul from UserLog ul inner join ul.user u";
		String orderByStr = "";
		TypedQuery<UserLog> query = null;
		
		// Check if sorting exists and set if so
		if ( sort != null ) {
			if (sort.equals("asc") || sort.equals("desc")) {
				orderByStr = " order by ul.dateLoggedIn " + sort + " ";
			}
		}
		
		// Check if for regular user or admin use
		if ( email != null ) {
			query = currentSession().createQuery(queryStr + " where email = :email" + orderByStr);
			query.setParameter("email", email);
		} else {
			query = currentSession().createQuery(queryStr + orderByStr);
		}
		
		// Check if pagination and limit requested
		if ( ((limit != null) && (limit.intValue() >= 0)) && ((page != null) && (page.intValue() >= 0)) ) {
			query.setFirstResult(page.intValue() * limit.intValue());
			query.setMaxResults(limit.intValue());
		}
		
		return query.getResultList();
	}
	
	//--------------------------------Thomas---------------------------------------------------------------
	@SuppressWarnings("unchecked")
	public List<UserLog> getAppLogPerAppPerTimeGap(int AppId,Date Start_DateTime,Date End_DateTime){
		TypedQuery<UserLog> query = null;
		query = currentSession().createQuery(" from UserLog ul where app_id = :app_id and ((ul.event=3 and ul.eventDate>= :start_event_datetime) or  (ul.event=4 and ul.eventDate<= :end_event_datetime)) ").setParameter("app_id", AppId).setParameter("start_event_datetime", Start_DateTime).setParameter("end_event_datetime", End_DateTime);
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserLog> getAppLogPerAppPerActiveUser(int AppId){
		TypedQuery<UserLog> query = null;
		query = currentSession().createQuery(" from UserLog ul where app_id = :app_id and (ul.event=3 or ul.event=4) ").setParameter("app_id", AppId);
		
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<UserLog> getAppLogPerUserPerTotalTime(int AppId){
		TypedQuery<UserLog> query = null;
		query = currentSession().createQuery(" from UserLog ul where app_id = :app_id and (ul.event=3  or ul.event=4) ").setParameter("app_id", AppId);
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserLog> getAppLogPerUserPerTotalPoints(int AppId){
		TypedQuery<UserLog> query = null;
		query = currentSession().createQuery(" from UserLog ul where app_id = :app_id and (ul.event=3 and ()) ").setParameter("app_id", AppId);
		
		return query.getResultList();
	}
	//--------------------------------Thomas---------------------------------------------------------------
}
