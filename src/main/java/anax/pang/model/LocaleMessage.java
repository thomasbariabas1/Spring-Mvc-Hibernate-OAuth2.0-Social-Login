package anax.pang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="localemessage")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LocaleMessage extends ResourceSupport {
	
	@Id
	@Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long localeMessageId;
	
	@ManyToOne
	@JoinColumn(name = "language_id")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Language language;
	
	@ManyToOne
	@JoinColumn(name = "locale_id")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Locale locale;
	
	@NotEmpty
	@Column(name = "message", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;

	public LocaleMessage() {
		super();
	}

	public LocaleMessage(Long localeMessageId) {
		super();
		this.localeMessageId = localeMessageId;
	}

	public LocaleMessage(Language language, Locale locale) {
		super();
		this.language = language;
		this.locale = locale;
	}
	
	public LocaleMessage(Long localeMessageId, Language language, Locale locale, String message) {
		super();
		this.localeMessageId = localeMessageId;
		this.language = language;
		this.locale = locale;
		this.message = message;
	}

	public Long getLocaleMessageId() {
		return localeMessageId;
	}

	public void setLocaleMessageId(Long localeMessageId) {
		this.localeMessageId = localeMessageId;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
