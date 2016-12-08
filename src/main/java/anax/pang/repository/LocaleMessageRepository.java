package anax.pang.repository;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import anax.pang.model.Language;
import anax.pang.model.Locale;
import anax.pang.model.LocaleMessage;

@Transactional
@Repository
public class LocaleMessageRepository {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public LocaleMessage getLocaleMessage(Long localeMessageId) {
		return currentSession().get(LocaleMessage.class, localeMessageId);
	}
	
	public LocaleMessage getLocaleMessage(Integer localeId, Integer languageId) {
		try {
			return (LocaleMessage) currentSession().createQuery("from LocaleMessage lm where lm.language.languageId = :languageId and lm.locale.localeId = :localeId", LocaleMessage.class)
															.setParameter("languageId", languageId)
															.setParameter("localeId", localeId)
															.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<LocaleMessage> getLocaleMessagesByLocale(Integer localeId) {
		return currentSession().createQuery("from LocaleMessage lm where lm.locale.localeId = :localeId", LocaleMessage.class)
														.setParameter("localeId", localeId)
														.getResultList();
	}
	
	public List<LocaleMessage> getLocaleMessagesByLanguage(Integer languageId) {
		return currentSession().createQuery("from LocaleMessage lm where lm.language.languageId = :languageId", LocaleMessage.class)
														.setParameter("languageId", languageId)
														.getResultList();
	}
	
	public List<LocaleMessage> getLocaleMessages() {
		return currentSession().createQuery("from LocaleMessage", LocaleMessage.class)
				.getResultList();
	}
	
	public Long createLocaleMessage(Integer localeId, Integer languageId, LocaleMessage localeMessage) {
		localeMessage.setLocaleMessageId(null);
		localeMessage.setLocale(currentSession().get(Locale.class, localeId));
		localeMessage.setLanguage(currentSession().get(Language.class, languageId));
		
		return (Long) currentSession().save(localeMessage);
	}
	
	/*public List<LocaleMessage> createLocaleMessages(List<LocaleMessage> localeMessages) {
		for (LocaleMessage localeMessage : localeMessages) {
			localeMessage.setLocaleMessageId(null);
			
			localeMessage.setLocaleMessageId((Long)currentSession().save(localeMessage));
		}
		
		return localeMessages;
	}*/
	
	public LocaleMessage updateLocaleMessage(Integer localeId, Integer languageId, LocaleMessage localeMessage) {
		localeMessage.setLocale(currentSession().get(Locale.class, localeId));
		localeMessage.setLanguage(currentSession().get(Language.class, languageId));
		
		return (LocaleMessage) currentSession().merge("LocaleMessage", localeMessage);
	}
	
	public void deleteLocaleMessage(LocaleMessage localeMessage) {
		currentSession().delete(localeMessage);
	}
}
