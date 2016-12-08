package anax.pang.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_REQUIRED, reason = "No such object")
public class UnbeatenPreviousLevelException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	Map<String, String> reportMap = new HashMap<String, String>();
	
	public UnbeatenPreviousLevelException(String message) {
		super(message);
	}

	public Map<String, String> getReportMap() {
		return reportMap;
	}

	public void setReportMap(Map<String, String> reportMap) {
		this.reportMap = reportMap;
	}
	
}
