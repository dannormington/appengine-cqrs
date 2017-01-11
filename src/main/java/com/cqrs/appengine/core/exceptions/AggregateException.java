package com.cqrs.appengine.core.exceptions;

import java.util.UUID;

/**
 * Base exception class for aggregate based exceptions
 *
 */
public abstract class AggregateException extends Exception {

	private static final String ERROR_FORMAT = "Aggregate Id : %s - Message: %s";
	
	private static final long serialVersionUID = 1L;
	
	private UUID aggregateId;
	
	/**
	 * Constructor
	 * 
	 * @param aggregateId
	 * @param message
	 */
	public AggregateException(UUID aggregateId, String message) {
		super(message);
		this.aggregateId = aggregateId;
	}
	
	/**
     * Get the aggregate Id
     * 
     * @return
     */
    public UUID getAggregateId(){
        return aggregateId;
    }
    
    /**
     * Get the error message
     */
    public String getMessage(){
    	
    	String aggregateIdString = aggregateId == null ? "NOT SPECIFIED" : aggregateId.toString();
    	
    	return String.format(ERROR_FORMAT, aggregateIdString, super.getMessage());
    }
}
