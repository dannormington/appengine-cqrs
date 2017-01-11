package com.cqrs.appengine.core.persistence;

import java.util.UUID;

import com.cqrs.appengine.core.domain.AggregateRoot;
import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.exceptions.InvalidParametersException;

/**
 * Interface for a repository implementation
 * 
 * @param <T>
 */
public interface Repository<T extends AggregateRoot> {

    /**
     * Persists the aggregate
     * 
     * @param aggregate
     * @throws EventCollisionException
     */
    void save(T aggregate) throws EventCollisionException;

    /**
     * Get the aggregate
     * 
     * @param id
     * @return
     * @throws HydrationException
     * @throws AggregateNotFoundException
     * @throws InvalidParametersException
     */
    T getById(UUID id) throws HydrationException, AggregateNotFoundException, InvalidParametersException;
}
