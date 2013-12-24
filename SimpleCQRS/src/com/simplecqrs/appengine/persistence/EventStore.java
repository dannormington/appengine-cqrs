package com.simplecqrs.appengine.persistence;

import java.util.UUID;

import com.simplecqrs.appengine.messaging.Event;

/**
 * Interface to support basic event store functionality
 */
public interface EventStore {
	
	/**
	 * Persist the events to the ES
	 * @param aggregateId
	 * @param expectedVersion
	 * @param events
	 */
	void saveEvents(UUID aggregateId, int expectedVersion, Iterable<Event> events);
	
	/**
	 * Get the events from the ES
	 * @param aggregateId
	 * @return
	 */
	Iterable<Event> getEvents(UUID aggregateId); 
}
