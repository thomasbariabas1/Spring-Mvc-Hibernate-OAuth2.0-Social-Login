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
@Table(name="language")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Language extends ResourceSupport {

	@Id
	@Column(name = "language_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer languageId;
	
	@NotEmpty
	@Column(name = "language_name", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;

	public Language() {
		super();
	}
	
	public Language(Integer languageId) {
		super();
		this.languageId = languageId;
	}

	public Language(Integer id, String name) {
		super();
		this.languageId = id;
		this.name = name;
	}
	
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
