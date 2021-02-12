package fr.formation.training.forum;

import java.time.LocalDateTime;
import java.util.*;

public class ApiError {

    private String message;

    private LocalDateTime timestamp = LocalDateTime.now();

    private List<ValidationError> errors = Collections.emptyList();

    private boolean validationErrors = false;

    public ApiError(String message) {
	this.message = message;
    }

    public ApiError(String message, List<ValidationError> errors) {
	this.message = message;
	this.errors = errors;
	validationErrors = true;
    }

    public LocalDateTime getTimestamp() {
	return timestamp;
    }

    public String getMessage() {
	return message;
    }

    public List<ValidationError> getErrors() {
	return errors;
    }

    public boolean isValidationErrors() {
	return validationErrors;
    }
}
