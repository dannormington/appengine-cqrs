package com.simplecqrs.appengine.domain;

import java.util.UUID;

import com.simplecqrs.appengine.messaging.Event;

/**
 * Simple interface to an aggregate root
 */
public interface AggregateRoot {
	
	/**
	 * get the Id
	 * 
	 * @return
	 */
	UUID getId();
	
	/**
	 * Gets all change events since the
	 * original hydration. If there are no
	 * changes then null is returned
	 * 
	 * @return
	 */
	Iterable<Event> getUncommittedChanges();
	
	/**
	 * Mark all changes a committed
	 */
	void markChangesAsCommitted();
	
	/**
	 * load the aggregate root
	 * 
	 * @param history
	 */
	void loadFromHistory(Iterable<Event> history);
	
	/**
	 * Returns the version of the aggregate when it was hydrated
	 * @return
	 */
	int getExpectedVersion();
}