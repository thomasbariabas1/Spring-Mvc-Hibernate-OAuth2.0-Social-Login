package anax.pang.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Error {

	private int code;
	private String message;
	private String documentation;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, String> links = new HashMap<String, String>();
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors = new ArrayList<String>();

	public Error() {
		super();
	}
	
	public Error(int code, String message, String documentation) {
		super();
		this.code = code;
		this.message = message;
		this.documentation = documentation;
	}

	public Error(int code, String message, String documentation, List<String> errors) {
		super();
		this.code = code;
		this.message = message;
		this.documentation = documentation;
		this.errors = errors;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDocumentation() {
		return documentation;
	}
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public Map<String, String> getLinks() {
		return links;
	}

	public void setLinks(Map<String, String> links) {
		this.links = links;
	}
}
