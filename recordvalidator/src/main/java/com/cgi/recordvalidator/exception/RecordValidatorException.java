package com.cgi.recordvalidator.exception;

/**
 *Custom exception class to show an error message 
 *
 */
public class RecordValidatorException extends Exception {
	private static final long serialVersionUID = 5109927478739721939L;

	public RecordValidatorException(String errorMessage) {
	        super(errorMessage);
	    }
}
