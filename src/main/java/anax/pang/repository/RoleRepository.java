package anax.pang.repository;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import anax.pang.model.Role;

@Transactional
@Repository
public class RoleRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session currentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public Role getRole(int roleId) {
		return currentSession().get(Role.class, roleId);
	}
	
	public Role getRole(String name) {
		try { 
			return (Role) currentSession().createQuery("from Role r where r.name = :name")
					.setParameter("name", name)
					.getSingleResult(); 
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Role> getRoles() {
		return currentSession().createQuery("from Role", Role.class).getResultList();
	}
}
