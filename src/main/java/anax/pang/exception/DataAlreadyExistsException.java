package anax.pang.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Object already exists")
public class DataAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	Map<String, String> docMap = new HashMap<String, String>();

	public DataAlreadyExistsException(String message) {
		super(message);
	}

	public Map<String, String> getDocMap() {
		return docMap;
	}

	public void setDocMap(Map<String, String> docMap) {
		this.docMap = docMap;
	}
}
