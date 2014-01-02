package com.simplecqrs.appengine.persistence;

import java.util.UUID;

/**
 * Exception that is thrown when the hydration of an aggregate fails.
 */
public class AggregateHydrationException extends Exception {
	
	private static final String ERROR_TEXT = "Loading the data failed";
	
	private static final long serialVersionUID = 1L;
	
	private UUID aggregateId;
	
	/**
	 * Default constructor
	 * 
	 * @param aggregateId
	 */
	public AggregateHydrationException(UUID aggregateId){
		super(ERROR_TEXT);
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
}
