package com.cqrs.appengine.core.persistence;

import java.util.UUID;

import com.cqrs.appengine.core.domain.AggregateRoot;
import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.messaging.Event;

/**
 * Implementation of a simple event repository
 * 
 * @param <T>
 */
public class EventRepository<T extends AggregateRoot> implements Repository<T> {

    /**
     * Instance of the event store
     */
    private EventStore eventStore;

    /**
     * The class type that the repository is working with
     */
    private Class<T> aClass;

    /**
     * Default Constructor
     * 
     * @param aClass
     */
    public EventRepository(Class<T> aClass){
        this.aClass = aClass;
        eventStore = new AppEngineEventStore();
    }

    /**
     * Constructor to be used when specifying a specific
     * queue for events to be published to
     * 
     * @param aClass
     */
    public EventRepository(Class<T> aClass, String queue){
        this.aClass = aClass;
        eventStore = new AppEngineEventStore(queue);
    }

    @Override
    public void save(T aggregate) throws EventCollisionException {
        eventStore.saveEvents(aggregate.getId(), aggregate.getExpectedVersion(), aggregate.getUncommittedChanges());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public T getById(UUID id) throws HydrationException, AggregateNotFoundException {

        /*
         * get the events from the event store
         */
        Iterable<Event> history = eventStore.getEvents(id);
        
        /*
         * Create a new instance of the aggregate
         */
        T aggregate;
		try {
			aggregate = this.aClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new HydrationException(id);
		}
        
        aggregate.loadFromHistory(history);

        return aggregate;
    }
}
