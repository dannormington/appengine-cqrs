package com.google.appengine.cqrs.persistence;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.UUID;

import com.google.appengine.cqrs.domain.AggregateRoot;
import com.google.appengine.cqrs.messaging.Event;

/**
 * Implementation of a simple event repository
 * @param <T>
 */
public class EventRepository<T extends AggregateRoot> implements Repository<T> {

	private EventStore _eventStore;
	private Class<T> _aClass;
	
	public EventRepository(Class<T> aClass){
		_aClass = aClass;
		//_eventStore = new SimpleEventStore(aClass.getSimpleName());
	}
	
	@Override
	public void save(T aggregate) {
		_eventStore.saveEvents(aggregate.getId(), aggregate.getExpectedVersion(), aggregate.getUncommittedChanges());
		aggregate.markChangesAsCommitted();
	}

	@Override
	public T getById(UUID id) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		T aggregate = null;

		/*
		 * get the events from the event store
		 */
		Iterable<Event> history = _eventStore.getEvents(id);
		
		/*
		 * create a new instance of the object by calling
		 * the constructor that takes the aggregate's id
		 */
		Constructor<T> constructor = _aClass.getDeclaredConstructor(UUID.class);
		constructor.setAccessible(true);
		aggregate = constructor.newInstance(id);
		
		/*
		 * load the aggregate with the events
		 */
		aggregate.loadFromHistory(history);
			
		return aggregate;
	}
}
