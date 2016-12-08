package anax.pang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="locale")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Locale extends ResourceSupport {
	
	@Id
	@Column(name = "locale_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer localeId;
	
	@NotEmpty
	@Column(name = "locale_var", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String var;
	
	public Locale() {
		super();
	}
	
	public Locale(Integer localeId) {
		super();
		this.localeId = localeId;
	}

	public Locale(String var) {
		super();
		this.var = var;
	}

	public Integer getLocaleId() {
		return localeId;
	}

	public void setLocaleId(Integer localeId) {
		this.localeId = localeId;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
}
