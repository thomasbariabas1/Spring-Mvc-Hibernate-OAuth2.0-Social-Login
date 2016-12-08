package anax.pang.repository;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import anax.pang.model.Locale;

@Transactional
@Repository
public class LocaleRepository {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	public Locale getLocale(Integer localeId) {
		return currentSession().get(Locale.class, localeId);
	}

	public Locale getLocale(String var) {
		try {
			return (Locale) currentSession().createQuery("from Locale l where l.var = :var", Locale.class)
									.setParameter("var", var).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Locale> getLocales() {
		return currentSession().createQuery("from Locale", Locale.class)
						.getResultList();
	}

	public Integer createLocale(Locale locale) {
		locale.setLocaleId(null);

		return (Integer) currentSession().save(locale);
	}

	public Locale updateLocale(Locale locale) {
		return (Locale) currentSession().merge("Locale", locale);
	}
	
	public void deleteLocale(Locale locale) {
		currentSession().delete(locale);
	}
}
