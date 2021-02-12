package fr.formation.training.forum;

import java.util.*;

import org.apache.commons.logging.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    private final static Log LOGGER = LogFactory
	    .getLog(CustomControllerAdvice.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
	    MethodArgumentNotValidException ex, HttpHeaders headers,
	    HttpStatus status, WebRequest request) {
	LOGGER.trace("400 BAD REQUEST - VALIDATION ERROR", ex);
	ApiError body = buildValidationApiError(status, ex);
	return handleExceptionInternal(ex, body, null, status, request);
    }

    private static ApiError buildValidationApiError(HttpStatus status,
	    MethodArgumentNotValidException ex) {
	BindingResult result = ex.getBindingResult();
	List<FieldError> fieldErrors = result.getFieldErrors();
	List<ValidationError> validationErrors = new ArrayList<>();
	for (FieldError error : fieldErrors) {
	    String violation = error.getCode();
	    String input = error.getField();
	    validationErrors.add(new ValidationError(violation, input, false));
	}
	List<ObjectError> globalErrors = result.getGlobalErrors();
	for (ObjectError error : globalErrors) {
	    String violation = error.getCode();
	    String input = error.getObjectName();
	    validationErrors.add(new ValidationError(violation, input, true));
	}
	return new ApiError("Validation error, STP revois ta saisie mon pote !",
		validationErrors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(
	    ResourceNotFoundException ex, WebRequest request) {
	LOGGER.debug("##### CUSTOM MESSAGE");
	return handleExceptionInternal(ex, null, null, HttpStatus.NOT_FOUND,
		request);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex,
	    WebRequest request) {
	LOGGER.error("##### CUSTOM MESSAGE", ex);
	return handleExceptionInternal(ex, null, null,
		HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
	    Object body, HttpHeaders headers, HttpStatus status,
	    WebRequest request) {
	if (body == null) {
	    body = new ApiError(status.getReasonPhrase());
	}
	return super.handleExceptionInternal(ex, body, headers, status,
		request);
    }
}
