package anax.pang.repository;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import anax.pang.model.Event;

@Transactional
@Repository
public class EventRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Event getEvent(Integer eventId) {
		return currentSession().get(Event.class, eventId);
	}
	
	public Event getEventByName(String name) {
		try {
			return (Event) currentSession().createQuery("from Event e where e.name = :name").setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
