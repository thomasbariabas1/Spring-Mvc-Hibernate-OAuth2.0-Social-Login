package anax.pang.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such object")
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	Map<String, String> docMap = new HashMap<String, String>();

	public DataNotFoundException(String message) {
		super(message);
	}

	public Map<String, String> getDocMap() {
		return docMap;
	}

	public void setDocMap(Map<String, String> docMap) {
		this.docMap = docMap;
	}
}
