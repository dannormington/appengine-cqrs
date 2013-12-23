package com.google.appengine.cqrs.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import com.google.appengine.cqrs.messaging.Event;

/**
 * Interface to support basic aggregate root functionality
 */
public interface AggregateRoot {
	
	/**
	 * Get the id of the aggregate
	 * @return
	 */
	UUID getId();
	
	/**
	 * Get a list of all the changes that have occurred since the aggregate was loaded
	 * @return
	 */
	Iterable<Event> getUncommittedChanges();
	
	/**
	 * Mark all changes as committed
	 */
	void markChangesAsCommitted();
	
	/**
	 * Load the aggregate from a list of events
	 * 
	 * @param history
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	void loadFromHistory(Iterable<Event> history) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	/**
	 * Returns the expected version of the first new event
	 * @return
	 */
	int getExpectedVersion();
}
