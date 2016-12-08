package anax.pang.repository;

import java.util.List;
import javax.persistence.NoResultException;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import anax.pang.controller.AppRestController;
import anax.pang.exception.DataAlreadyExistsException;
import anax.pang.exception.DataNotFoundException;
import anax.pang.model.App;

@Transactional
@Repository
public class AppRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public App getApp(Integer appId) {
		return currentSession().get(App.class, appId);
	}
	
	public App getAppByAppId(Integer appId, Boolean appInfo_MODE) {
		App app = null;
		try {
			if ( appInfo_MODE ) {
				app = currentSession().createNamedQuery("App.findByAppId_E", App.class).setParameter("appId", appId).getSingleResult();
				Hibernate.initialize(app.getListOfAppInfo());
			} else {
				app = currentSession().createNamedQuery("App.findByAppId_L", App.class).setParameter("appId", appId).getSingleResult();
			}
		} catch (NoResultException e) {
			throwDataNotFoundException();
		}
		
		return app;
	}
	
	public App getAppByAppName(String appName, Boolean appInfo_MODE) {
		App app = null;
		try {
			if ( appInfo_MODE ) {
				app = currentSession().createNamedQuery("App.findByAppName_E", App.class).setParameter("appName", appName).getSingleResult();
				Hibernate.initialize(app.getListOfAppInfo());
			} else {
				app = currentSession().createNamedQuery("App.findByAppName_L", App.class).setParameter("appName", appName).getSingleResult();
			}
		} catch (NoResultException e) {
			throwDataNotFoundException();
		}
		
		return app;
	}
	
	public List<App> getApps() {
		return currentSession().createNamedQuery("App.findAll", App.class).getResultList();
	}
	
	public Integer createApp(App app) {
		App searchApp = null;
		try {
			searchApp = currentSession().createNamedQuery("App.findByAppName_L", App.class).setParameter("appName", app.getAppName()).getSingleResult();
			throwDataAlreadyExistsException(app);
		} catch (NoResultException e) {
			return (Integer) currentSession().save(app);
		}
		
		return searchApp.getAppId();
	}
	
	public App updateApp(App app) {
		try {
			currentSession().createNamedQuery("App.findByAppId_L", App.class).setParameter("appId", app.getAppId()).getSingleResult();
		} catch (NoResultException e) {
			throwDataNotFoundException();
		}
		
		return (App) currentSession().merge(app);
	}
	
	public void deleteApp(App app) {
		currentSession().delete(app);
	}
	
	
	// Throw exception handlers
	private void throwDataNotFoundException() {
		DataNotFoundException exception = new DataNotFoundException("App not found.");
		exception.getDocMap().put("collection", ControllerLinkBuilder.linkTo(AppRestController.class).slash("").toString());
		throw exception;
	}
	
	private void throwDataAlreadyExistsException(App app) {
		DataAlreadyExistsException exception = new DataAlreadyExistsException("App already exists.");
		exception.getDocMap().put("collection", ControllerLinkBuilder.linkTo(AppRestController.class).slash("").toString());
		exception.getDocMap().put("self", ControllerLinkBuilder.linkTo(AppRestController.class).slash(app.getAppId()).toString());
		exception.getDocMap().put("self-name", ControllerLinkBuilder.linkTo(AppRestController.class).slash("name=" + app.getAppName()).toString());
		throw exception;
	}
}
