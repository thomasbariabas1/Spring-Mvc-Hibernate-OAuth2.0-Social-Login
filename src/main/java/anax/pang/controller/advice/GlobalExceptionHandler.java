package anax.pang.controller.advice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import anax.pang.exception.DataAlreadyExistsException;
import anax.pang.exception.DataNotFoundException;
import anax.pang.exception.UnbeatenPreviousLevelException;
import anax.pang.model.Error;

import javax.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final String documentation = "http://localhost:8080/pang/swagger-ui.html";
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<Error> handleDataNotFoundException(DataNotFoundException exception) {
		Error error = new Error(404, exception.getMessage(), documentation);
		error.setLinks(exception.getDocMap());
		
		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataAlreadyExistsException.class)
	public ResponseEntity<Error> handleDataAlreadyExistsException(DataAlreadyExistsException exception) {
		Error error = new Error(409, exception.getMessage(), documentation);
		error.setLinks(exception.getDocMap());
		return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(UnbeatenPreviousLevelException.class)
	public ResponseEntity<Error> handleUnbeatenPreviousLevelException(UnbeatenPreviousLevelException exception) {
		Error error = new Error(428, exception.getMessage(), documentation);
		error.setLinks(exception.getReportMap());
		return new ResponseEntity<Error>(error, HttpStatus.PRECONDITION_REQUIRED);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
	protected Error handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		Error error = new Error(422, exception.getLocalizedMessage().toString(), documentation);
		
		for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {
            error.getErrors().add(objectError.getDefaultMessage().toString());
		}

		return error;
    }
	
	@ExceptionHandler(HibernateException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
	protected Error handleHibernateException(HibernateException exception) {
		Error error = new Error(500, exception.getLocalizedMessage().toString(), documentation);
		return error;
    }
	
	@ExceptionHandler(NoResultException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
	protected Error handleNoResultException(NoResultException exception) {
		Error error = new Error(404, exception.getLocalizedMessage().toString(), documentation);
		return error;
    }
	
}
