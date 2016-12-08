package anax.pang.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import anax.pang.model.LocaleMessage;
import anax.pang.service.LocaleMessageService;

@RestController
@RequestMapping("/api/user0/localemessages")
public class LocaleMessageRestController {

	@Autowired
	private LocaleMessageService localeMessageService;
	
	@GetMapping("/{localeMessageId}")
	public LocaleMessage getLocaleMessage(	@PathVariable Long localeMessageId	) {
		return addLinks(localeMessageService.getLocaleMessage(localeMessageId));
	}
	
	@GetMapping("")
	public ResponseEntity<List<?>> getLocaleMessages(	@RequestHeader(value = "localeId", required = false)  Integer localeId,
														@RequestHeader(value = "languageId", required = false )  Integer languageId	) {
		List<LocaleMessage> localeMessages = new ArrayList<LocaleMessage>();
		HttpStatus httpStatus = HttpStatus.OK;
		
		if ( localeId == null && languageId == null) {
			localeMessages = localeMessageService.getLocaleMessages();
		} else if ( localeId == null) {
			localeMessages = localeMessageService.getLocaleMessagesByLanguage(languageId);
		} else if ( languageId == null ) {
			localeMessages = localeMessageService.getLocaleMessagesByLocale(localeId);
		} else {
			localeMessages.add(localeMessageService.getLocaleMessage(localeId, languageId));
		}
		
		if ( localeMessages.size() <= 0 ) {
			httpStatus = HttpStatus.NO_CONTENT;
		}
		
		return new ResponseEntity<List<?>>(localeMessages, httpStatus);
	}
	
	@PostMapping("")
	@ResponseStatus(value = HttpStatus.CREATED)
	public LocaleMessage createLocaleMessage(	@RequestHeader(value = "localeId")  Integer localeId,
												@RequestHeader(value = "languageId")  Integer languageId,
												@Valid @RequestBody LocaleMessage localeMessage) {
		return addLinks(localeMessageService.createLocaleMessage(localeId, languageId, localeMessage));
	}
	
	/*@PostMapping("/multi")
	public List<LocaleMessage> createLocaleMessages(	@Valid @RequestBody List<LocaleMessage> localeMessages) {
		return localeMessageService.createLocaleMessages(localeMessages);
	}*/
	
	@PutMapping("/{localeMessageId}")
	public LocaleMessage updateLocaleMessage(	@PathVariable Long localeMessageId,
												@RequestHeader(value = "localeId")  Integer localeId,
												@RequestHeader(value = "languageId")  Integer languageId,
												@Valid @RequestBody LocaleMessage localeMessage) {
		localeMessage.setLocaleMessageId(localeMessageId);
		
		return addLinks(localeMessageService.updateLocaleMessage(localeId, languageId, localeMessage));	
	}
	
	@DeleteMapping("/{localeMessageId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "LocaleMessage deletion")
	public void deleteLocaleMessage(	@PathVariable Long localeMessageId	) {
		localeMessageService.deleteLocaleMessage(localeMessageId);
	}

	// HATEOAS Methods
	private void addSelfLink(LocaleMessage localeMessage) {
		localeMessage.add(ControllerLinkBuilder.linkTo(LocaleMessageRestController.class).slash(localeMessage.getLocaleMessageId()).withSelfRel());
	}

	private void addCollectionLink(LocaleMessage localeMessage) {
		localeMessage.add(ControllerLinkBuilder.linkTo(LocaleMessageRestController.class).slash("").withRel("collection"));
	}

	private void addRefCollectionsLink(LocaleMessage localeMessage) {
		localeMessage.add(ControllerLinkBuilder.linkTo(LanguageRestController.class)
						.slash(localeMessage.getLanguage().getLanguageId())
						.slash("localemessages")
					.withRel("find-by-language-collection"));
		localeMessage.add(ControllerLinkBuilder.linkTo(LocaleRestController.class)
						.slash(localeMessage.getLocale().getLocaleId())
						.slash("localemessages")
					.withRel("find-by-locale-collection"));
	}

	private LocaleMessage addLinks(LocaleMessage localeMessage) {
		addSelfLink(localeMessage);
		addCollectionLink(localeMessage);
		addRefCollectionsLink(localeMessage);
		
		return localeMessage;
	}
}
