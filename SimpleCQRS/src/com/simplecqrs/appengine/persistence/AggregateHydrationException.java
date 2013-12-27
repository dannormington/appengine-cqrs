package com.simplecqrs.appengine.persistence;

import java.util.UUID;

/**
 * Exception that is thrown when the hydration of an aggregate fails.
 */
@SuppressWarnings("serial")
public class AggregateHydrationException extends Exception {
	
	private UUID aggregateId;
	
	/**
	 * Default constructor
	 * 
	 * @param aggregateId
	 */
	public AggregateHydrationException(UUID aggregateId){
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
