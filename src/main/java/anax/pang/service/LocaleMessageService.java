package anax.pang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import anax.pang.controller.LanguageRestController;
import anax.pang.controller.LocaleMessageRestController;
import anax.pang.controller.LocaleRestController;
import anax.pang.exception.DataAlreadyExistsException;
import anax.pang.exception.DataNotFoundException;
import anax.pang.model.LocaleMessage;
import anax.pang.repository.LocaleMessageRepository;

@Service
public class LocaleMessageService {
	
	@Autowired
	private LocaleMessageRepository localeMessageRepository;

	@Autowired
	private LocaleService localeService;
	
	@Autowired
	private LanguageService languageService;
	
	public LocaleMessageRepository getLocaleMessageRepository() {
		return localeMessageRepository;
	}

	public LocaleService getLocaleService() {
		return localeService;
	}

	public LanguageService getLanguageService() {
		return languageService;
	}

	public LocaleMessage getLocaleMessage(Long localeMessageId) {
		LocaleMessage localeMessage = getLocaleMessageRepository().getLocaleMessage(localeMessageId);
		
		if (localeMessage == null) {
			throwDataNotFoundException();
		}
		
		return localeMessage;
	}
	
	public LocaleMessage getLocaleMessage(Integer localeId, Integer languageId) {
		LocaleMessage localeMessage = getLocaleMessageRepository().getLocaleMessage(localeId, languageId);
		if (localeMessage == null) {
			throwDataNotFoundException();
		}
		return localeMessage;
	}
	
	public List<LocaleMessage> getLocaleMessagesByLocale(Integer localeId) {
		return getLocaleMessageRepository().getLocaleMessagesByLocale(localeId);
	}
	
	public List<LocaleMessage> getLocaleMessagesByLanguage(Integer languageId) {
		return getLocaleMessageRepository().getLocaleMessagesByLanguage(languageId);
	}
	
	public List<LocaleMessage> getLocaleMessages() {
		return getLocaleMessageRepository().getLocaleMessages();
	}

	public LocaleMessage createLocaleMessage(Integer localeId, Integer languageId, LocaleMessage localeMessage) {
		LocaleMessage searchLocaleMessage = getLocaleMessageRepository().getLocaleMessage(localeId, languageId);
		if ( searchLocaleMessage != null ) {
			throwDataAlreadyExistsException(searchLocaleMessage);
		}
		
		localeMessage.setLocaleMessageId(getLocaleMessageRepository().createLocaleMessage(localeId, languageId, localeMessage));
		return localeMessage;
	}
	
	/*public List<LocaleMessage> createLocaleMessages(List<LocaleMessage> localeMessages) {
		return getLocaleMessageRepository().createLocaleMessages(localeMessages);
	}*/
	
	public LocaleMessage updateLocaleMessage(Integer localeId, Integer languageId, LocaleMessage localeMessage) {
		localeMessage.setLocale(localeService.getLocale(localeId));
		localeMessage.setLanguage(languageService.getLanguage(languageId));
		
		if ( getLocaleMessageRepository().getLocaleMessage(localeMessage.getLocaleMessageId()) == null ) {
			throwDataNotFoundException();
		}
		
		return getLocaleMessageRepository().updateLocaleMessage(localeId, languageId, localeMessage);
	}
	
	public void deleteLocaleMessage(Long localeMessageId) {
		getLocaleMessageRepository().deleteLocaleMessage(new LocaleMessage(localeMessageId));
	}
	
	// Throw exception handlers
	private void throwDataNotFoundException() {
		DataNotFoundException exception = new DataNotFoundException("LocaleMessage not found.");
		Map<String, String> docMap = new HashMap<String, String>();
		docMap.put("collection", ControllerLinkBuilder.linkTo(LocaleMessageRestController.class).toString());
		exception.setDocMap(docMap);
		throw exception;
	}

	private void throwDataAlreadyExistsException(LocaleMessage localeMessage) {
		DataAlreadyExistsException exception = new DataAlreadyExistsException("LocaleMessage already exists.");
		Map<String, String> docMap = new HashMap<String, String>();
		docMap.put("collection", ControllerLinkBuilder.linkTo(LocaleMessageRestController.class).toString());
		docMap.put("self", ControllerLinkBuilder.linkTo(LocaleMessageRestController.class).slash(localeMessage.getLocaleMessageId()).toString());
		docMap.put("find-language-collection", ControllerLinkBuilder.linkTo(LanguageRestController.class).slash(localeMessage.getLanguage().getLanguageId()).slash("localemessages").toString());
		docMap.put("find-locale-collection", ControllerLinkBuilder.linkTo(LocaleRestController.class).slash(localeMessage.getLocale().getLocaleId()).slash("localemessages").toString());
		exception.setDocMap(docMap);
		throw exception;
	}
}
