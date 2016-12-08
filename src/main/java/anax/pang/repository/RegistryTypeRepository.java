package anax.pang.repository;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import anax.pang.model.RegistryType;

@Transactional
@Repository
public class RegistryTypeRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session currentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public RegistryType getRegistryType(int registryTypeId) {
		return currentSession().get(RegistryType.class, registryTypeId);
	}
	
	public RegistryType getRegistryType(String shortName) {
		try { 
			return (RegistryType) currentSession().createQuery("from RegistryType rt where rt.shortName = :shortName")
					.setParameter("shortName", shortName)
					.getSingleResult(); 
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<RegistryType> getRegistryTypes() {
		return currentSession().createQuery("from RegistryType", RegistryType.class).getResultList();
	}
}
