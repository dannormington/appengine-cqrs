package com.cqrs.appengine.core.exceptions;

import java.util.UUID;

/**
 * Exception that is thrown when the specified aggregate Id is not found
 *
 */
public class AggregateNotFoundException extends AggregateException {

	private static final String ERROR_TEXT = "The aggregate you requested cannot be found.";
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param aggregateId
	 */
	public AggregateNotFoundException(UUID aggregateId) {
		super(aggregateId, ERROR_TEXT);
	}

}
