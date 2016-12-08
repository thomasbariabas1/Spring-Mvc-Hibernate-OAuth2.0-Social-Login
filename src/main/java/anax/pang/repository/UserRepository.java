package anax.pang.repository;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import anax.pang.model.User;

@Transactional
@Repository
public class UserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	public User getUser(Integer id) {
		return currentSession().get(User.class, id);
	}

	public User getUser(String email) { 
		try {		
			return (User) currentSession().createQuery("from User u where u.email = :email")
					.setParameter("email", email)
					.getSingleResult();
		} catch ( NoResultException e ) {
			return null;
		}
	}

	public List<User> getUsers() {
		return currentSession().createQuery("from User", User.class).getResultList();
	}

	public Integer createUser(User user) {
		return (Integer) currentSession().save(user);
	}

	public User updateUserInfo(User user) {
		return (User) currentSession().merge(user);
	}

}
