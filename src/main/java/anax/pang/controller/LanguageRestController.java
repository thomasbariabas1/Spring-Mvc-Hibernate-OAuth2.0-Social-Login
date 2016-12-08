package anax.pang.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import anax.pang.model.Language;
import anax.pang.model.LocaleMessage;
import anax.pang.service.LanguageService;
import anax.pang.service.LocaleMessageService;

@RestController
@RequestMapping("/api/user0/languages")
public class LanguageRestController {

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private LocaleMessageService localeMessageService;
	
	@GetMapping("/{languageId}")
	public Language getLanguage(@PathVariable Integer languageId) {
		return addLinks(languageService.getLanguage(languageId));
	}
	
	@GetMapping("/name={name}")
	public Language getLanguage(@PathVariable String name) {
		return addLinks(languageService.getLanguage(name));
	}
	
	@GetMapping("")
	public ResponseEntity<List<?>> getLanguages() {
		List<Language> languages = languageService.getLanguages();
		HttpStatus httpStatus = HttpStatus.OK;
		
		if ( languages.size() <= 0 ) {
			httpStatus = HttpStatus.NO_CONTENT;
		}
		
		return new ResponseEntity<List<?>>(languages, httpStatus);
	}
	
	@PostMapping("")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Language createLanguage(@Valid @RequestBody Language language) {		
		return addLinks(languageService.createLanguage(language));
	}
	
	@PutMapping("/{languageId}")
	public Language updateLanguage(	@PathVariable Integer languageId, 
									@Valid @RequestBody Language language) {
		language.setLanguageId(languageId);
		
		return addLinks(languageService.updateLanguage(language));
	}
	
	@DeleteMapping("/{languageId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Language deletion")
	public void deleteLanguage(	@PathVariable Integer languageId ) {
		languageService.deleteLanguage(languageId);
	}
	
	@GetMapping("/{languageId}/localemessages")
	public ResponseEntity<List<?>> getLocaleMessagesByLocale(	@PathVariable Integer languageId) {
		List<LocaleMessage> localeMessages = localeMessageService.getLocaleMessagesByLanguage(languageService.getLanguage(languageId).getLanguageId());
		
		HttpStatus httpStatus = HttpStatus.OK;
		
		if ( localeMessages.size() <= 0 ) {
			httpStatus = HttpStatus.NO_CONTENT;
		}
		
		return new ResponseEntity<List<?>>(localeMessages, httpStatus);
	}
	
	// HATEOAS Methods
	private void addSelfLink(Language language) {
		language.add(ControllerLinkBuilder.linkTo(LanguageRestController.class).slash(language.getLanguageId()).withSelfRel());
	}
	
	private void addSelfNameLink(Language language) {
		language.add(ControllerLinkBuilder.linkTo(LanguageRestController.class).slash("name=" + language.getName()).withRel("self-name"));
	}

	private void addCollectionLink(Language language) {
		language.add(ControllerLinkBuilder.linkTo(LanguageRestController.class).slash("").withRel("collection"));
	}
	
	private void addChildLinks(Language language) {
		//language.add(ControllerLinkBuilder.linkTo( ControllerLinkBuilder.methodOn(LanguageRestController.class).getLocaleMessagesByLanguage(language.getLanguageId()) )
		language.add(ControllerLinkBuilder.linkTo(LanguageRestController.class).slash(language.getLanguageId()).slash("localemessages")
				.withRel("language-localemessages"));
		/*language.add(ControllerLinkBuilder.linkTo(LocaleMessageRestController.class).slash("")
				.withRel("localemessages"));*/
	}

	private Language addLinks(Language language) {
		addSelfLink(language);
		addSelfNameLink(language);
		addCollectionLink(language);
		addChildLinks(language);
		
		return language;
	}
}
