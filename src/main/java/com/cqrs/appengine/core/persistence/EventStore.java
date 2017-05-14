package com.cqrs.appengine.core.persistence;

import java.util.UUID;

import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.messaging.Event;

/**
 * Interface to support basic event store functionality
 */
interface EventStore {

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
     * @throws HydrationException
     * @throws AggregateNotFoundException
     */
    Iterable<Event> getEvents(UUID aggregateId) throws HydrationException, AggregateNotFoundException; 
}

