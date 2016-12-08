package anax.pang.repository;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import anax.pang.model.Language;

@Transactional
@Repository
public class LanguageRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Language getLanguage(Integer languageId) {
		return currentSession().get(Language.class, languageId);
	}
	
	public Language getLanguage(String name) {
		try {
			return (Language) currentSession().createQuery("from Language l where l.name = :name", Language.class)
									.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Language> getLanguages() {
		return currentSession().createQuery("from Language", Language.class).getResultList();
	}
	
	public Integer createLanguage(Language language) {
		language.setLanguageId(null);

		return (Integer) currentSession().save(language);
	}
	
	public Language updateLanguage(Language language) {
		return (Language) currentSession().merge("Language", language);
	}
	
	public void deleteLanguage(Language language) {
		currentSession().delete(language);
	}
}
