package com.google.appengine.cqrs.persistence;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import com.google.appengine.cqrs.domain.AggregateRoot;

/**
 * Interface for a repository implementation
 * 
 * @param <T>
 */
public interface Repository<T extends AggregateRoot> {
	
	/**
	 * Save the aggregate
	 * @param aggregate
	 */
	void save(T aggregate);
	
	/**
	 * Get the aggregate
	 * @param id
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	T getById(UUID id) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
