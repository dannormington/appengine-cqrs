package com.simplecqrs.appengine.persistence;

import java.util.UUID;

import com.simplecqrs.appengine.exceptions.EventCollisionException;
import com.simplecqrs.appengine.exceptions.HydrationException;
import com.simplecqrs.appengine.messaging.Event;

/**
 * Interface to support basic event store functionality
 */
public interface EventStore {
	
	/**
	 * Persist the changes
	 * 
	 * @param aggregateId
	 * @param expectedVersion
	 * @param events
	 * @throws EventCollisionException
	 */
	void saveEvents(UUID aggregateId, int expectedVersion, Iterable<Event> events) throws EventCollisionException;
	
	/**
	 * Retrieves the events
	 * 
	 * @param aggregateId
	 * @return
	 * @throws AggregateHydrationException
	 */
	Iterable<Event> getEvents(UUID aggregateId) throws HydrationException; 
}

