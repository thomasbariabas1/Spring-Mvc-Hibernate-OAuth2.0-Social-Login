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

import anax.pang.model.Locale;
import anax.pang.model.LocaleMessage;
import anax.pang.service.LocaleMessageService;
import anax.pang.service.LocaleService;

@RestController
@RequestMapping("/api/user0/locales")
public class LocaleRestController {

	@Autowired
	private LocaleService localeService;
	
	@Autowired
	private LocaleMessageService localeMessageService;

	@GetMapping("/{localeId}")
	public Locale getLocale(@PathVariable Integer localeId) {
		return addLinks(localeService.getLocale(localeId));
	}
	
	@GetMapping("/var={var}")
	public Locale getLocale(@PathVariable String var) {
		return addLinks(localeService.getLocale(var));
	}
	
	@GetMapping("")
	public ResponseEntity<List<?>> getLocales() {
		List<Locale> locales = localeService.getLocales();
		HttpStatus httpStatus = HttpStatus.OK;
		
		if ( locales.size() <= 0 ) {
			httpStatus = HttpStatus.NO_CONTENT;
		}
		
		return new ResponseEntity<List<?>>(locales, httpStatus);
	}
	
	@PostMapping("")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Locale createLocale(@Valid @RequestBody Locale locale) {		
		return addLinks(localeService.createLocale(locale));
	}
	
	@PutMapping("/{localeId}")
	public Locale updateLocale(	@PathVariable Integer localeId, 
								@Valid @RequestBody Locale locale) {
		locale.setLocaleId(localeId);
		
		return addLinks(localeService.updateLocale(locale));
	}
	
	@DeleteMapping("/{localeId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Locale deletion")
	public void deleteLocale(	@PathVariable Integer localeId) {
		localeService.deleteLocale(localeId);
	}
	
	@GetMapping("/{localeId}/localemessages")
	public ResponseEntity<List<?>> getLocaleMessagesByLocale(	@PathVariable Integer localeId) {
		List<LocaleMessage> localeMessages = localeMessageService.getLocaleMessagesByLocale(localeId);
		HttpStatus httpStatus = HttpStatus.OK;
		
		if ( localeMessages.size() <= 0 ) {
			httpStatus = HttpStatus.NO_CONTENT;
		}
		
		return new ResponseEntity<List<?>>(localeMessages, httpStatus);
	}

	// HATEOAS Methods
	private void addSelfLink(Locale locale) {
		locale.add(ControllerLinkBuilder.linkTo(LocaleRestController.class).slash(locale.getLocaleId())
				.withSelfRel());
	}

	private void addSelfVarLink(Locale locale) {
		locale.add(ControllerLinkBuilder.linkTo(LocaleRestController.class).slash("var=" + locale.getVar())
				.withRel("self-var"));
	}

	private void addCollectionLink(Locale locale) {
		locale.add(ControllerLinkBuilder.linkTo(LocaleRestController.class).slash("")
				.withRel("collection"));
	}
	
	private void addChildLinks(Locale locale) {
		//locale.add(ControllerLinkBuilder.linkTo( ControllerLinkBuilder.methodOn(LocaleRestController.class).getLocaleMessagesByLocale(locale.getLocaleId()) )
		locale.add(ControllerLinkBuilder.linkTo(LocaleRestController.class).slash(locale.getLocaleId()).slash("localemessages")
				.withRel("locale-localemessages"));
		/*locale.add(ControllerLinkBuilder.linkTo(LocaleMessageRestController.class).slash("")
				.withRel("localemessages"));*/
	}

	private Locale addLinks(Locale locale) {
		addSelfLink(locale);
		addSelfVarLink(locale);
		addCollectionLink(locale);
		addChildLinks(locale);

		return locale;
	}
}
