package anax.pang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import anax.pang.controller.LanguageRestController;
import anax.pang.exception.DataAlreadyExistsException;
import anax.pang.exception.DataNotFoundException;
import anax.pang.model.Language;
import anax.pang.repository.LanguageRepository;

@Service
public class LanguageService {

	@Autowired
	private LanguageRepository languageRepository;

	public LanguageRepository getLanguageRepository() {
		return languageRepository;
	}

	public Language getLanguage(Integer languageId) {
		Language language = getLanguageRepository().getLanguage(languageId);
		if (language == null) {
			throwDataNotFoundException();
		}
		return language;
	}
	
	public Language getLanguage(String name) {
		Language language = getLanguageRepository().getLanguage(name);
		if (language == null) {
			throwDataNotFoundException();
		}
		return language;
	}
	
	public List<Language> getLanguages() {
		return getLanguageRepository().getLanguages();
	}
	
	public Language createLanguage(Language language) {
		Language searchLanguage = getLanguageRepository().getLanguage(language.getName()); 
		if ( searchLanguage != null ) {
			throwDataAlreadyExistsException(searchLanguage);
		}
		
		language.setLanguageId(getLanguageRepository().createLanguage(language));
		return language;
	}
	
	public Language updateLanguage(Language language) {
		Language searchLanguage = getLanguageRepository().getLanguage(language.getLanguageId());
		if ( searchLanguage == null ) {
			throwDataNotFoundException();
		}
		
		searchLanguage = getLanguageRepository().getLanguage(language.getName());
		if ( searchLanguage != null ) {
			throwDataAlreadyExistsException(searchLanguage);
		}

		return getLanguageRepository().updateLanguage(language);
	}
	
	public void deleteLanguage(Integer languageId) {
		getLanguageRepository().deleteLanguage(new Language(languageId));
	}

	// Throw exception handlers
	private void throwDataNotFoundException() {
		DataNotFoundException exception = new DataNotFoundException("Language not found.");
		Map<String, String> docMap = new HashMap<String, String>();
		docMap.put("collection", ControllerLinkBuilder.linkTo(LanguageRestController.class).slash("").toString());
		exception.setDocMap(docMap);
		throw exception;
	}

	private void throwDataAlreadyExistsException(Language language) {
		DataAlreadyExistsException exception = new DataAlreadyExistsException("Language already exists.");
		Map<String, String> docMap = new HashMap<String, String>();
		docMap.put("collection", ControllerLinkBuilder.linkTo(LanguageRestController.class).slash("").toString());
		docMap.put("self", ControllerLinkBuilder.linkTo(LanguageRestController.class).slash(language.getLanguageId()).toString());
		docMap.put("self-name", ControllerLinkBuilder.linkTo(LanguageRestController.class).slash("name=" + language.getName()).toString());
		exception.setDocMap(docMap);
		throw exception;
	}
}
