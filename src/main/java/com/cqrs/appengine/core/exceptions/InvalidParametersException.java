package com.cqrs.appengine.core.exceptions;

import java.util.UUID;

/**
 * Exception that is thrown when the required parameters for behavior are not present.
 * 
 */
public class InvalidParametersException extends AggregateException {

	private static final String ERROR_TEXT = "The required parameters were not present.";
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param aggregateId
	 */
	public InvalidParametersException(UUID aggregateId) {
		super(aggregateId, ERROR_TEXT);
	}
	
	/**
	 * Constructor
	 * 
	 * @param aggregateId
	 * @param message
	 */
	public InvalidParametersException(UUID aggregateId, String message) {
		super(aggregateId, message);
	}

}
