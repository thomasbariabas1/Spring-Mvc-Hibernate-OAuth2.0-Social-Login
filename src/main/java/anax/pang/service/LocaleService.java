package anax.pang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import anax.pang.controller.LocaleRestController;
import anax.pang.exception.DataAlreadyExistsException;
import anax.pang.exception.DataNotFoundException;
import anax.pang.model.Locale;
import anax.pang.repository.LocaleRepository;

@Service
public class LocaleService {

	@Autowired
	private LocaleRepository localeRepository;

	public LocaleRepository getLocaleRepository() {
		return localeRepository;
	}
	
	public Locale getLocale(Integer localeId) {
		Locale locale = getLocaleRepository().getLocale(localeId);
		if (locale == null) {
			throwDataNotFoundException();
		}
		return locale;
	}
	
	public Locale getLocale(String var) {
		Locale locale = getLocaleRepository().getLocale(var);
		if (locale == null) {
			throwDataNotFoundException();
		}
		return locale;
	}
	
	public List<Locale> getLocales() {
		return getLocaleRepository().getLocales();
	}
	
	public Locale createLocale(Locale locale) {
		Locale searchLocale = getLocaleRepository().getLocale(locale.getVar()); 
		if ( searchLocale != null ) {
			throwDataAlreadyExistsException(searchLocale);
		}
		
		locale.setLocaleId(getLocaleRepository().createLocale(locale));
		return locale;
	}
	
	public Locale updateLocale(Locale locale) {
		if ( getLocaleRepository().getLocale(locale.getLocaleId()) == null ) {
			throwDataNotFoundException();
		}

		return getLocaleRepository().updateLocale(locale);
	}
	
	public void deleteLocale(Integer localeId) {
		getLocaleRepository().deleteLocale(new Locale(localeId));
	}
	
	// Throw exception handlers
	private void throwDataNotFoundException() {
		DataNotFoundException exception = new DataNotFoundException("Locale not found.");
		Map<String, String> docMap = new HashMap<String, String>();
		docMap.put("collection", ControllerLinkBuilder.linkTo(LocaleRestController.class).slash("").toString());
		exception.setDocMap(docMap);
		throw exception;
	}

	private void throwDataAlreadyExistsException(Locale locale) {
		DataAlreadyExistsException exception = new DataAlreadyExistsException("Locale already exists.");
		Map<String, String> docMap = new HashMap<String, String>();
		docMap.put("collection", ControllerLinkBuilder.linkTo(LocaleRestController.class).slash("").toString());
		docMap.put("self", ControllerLinkBuilder.linkTo(LocaleRestController.class).slash(locale.getLocaleId()).toString());
		docMap.put("self-var", ControllerLinkBuilder.linkTo(LocaleRestController.class).slash("var=" + locale.getVar()).toString());
		exception.setDocMap(docMap);
		throw exception;
	}
}
