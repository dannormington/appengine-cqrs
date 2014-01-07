package com.simplecqrs.appengine.persistence;

import java.util.UUID;

import com.simplecqrs.appengine.domain.AggregateRoot;

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
	 * @throws AggregateHydrationException
	 */
	T getById(UUID id) throws HydrationException;
}
