package fr.formation.training.forum;

public class ValidationError {

    private String violation;

    private String input;

    private boolean globalError;

    public ValidationError(String violation, String input,
	    boolean globalError) {
	this.violation = violation;
	this.input = input;
	this.globalError = globalError;
    }

    public String getViolation() {
	return violation;
    }

    public String getInput() {
	return input;
    }

    public boolean isGlobalError() {
	return globalError;
    }
}
