package com.simplecqrs.appengine.persistence;

import java.util.Date;
import java.util.UUID;

/**
 * Exception that is thrown when an event is attempted to be persisted
 * but the current event history has changed
 */
@SuppressWarnings("serial")
public class EventCollisionException extends Exception {

	private UUID aggregateId;
	private int expectedVersion;
	private Date dateOccurred;
	
	/**
	 * Default constructor
	 * 
	 * @param aggregateId
	 * @param expectedVersion
	 */
	public EventCollisionException(UUID aggregateId, int expectedVersion){
		this.aggregateId = aggregateId;
		this.expectedVersion = expectedVersion;
		this.dateOccurred = new Date();
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
	 * Get the expected version when the collision occurred
	 * 
	 * @return
	 */
	public int getExpectedVersion(){
		return expectedVersion;
	}
	
	/**
	 * Get the date the exception occurred
	 * 
	 * @return
	 */
	public Date getDateOccurred(){
		return dateOccurred;
	}
}
