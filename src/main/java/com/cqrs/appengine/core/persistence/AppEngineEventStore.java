package com.cqrs.appengine.core.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.messaging.Event;
import com.cqrs.appengine.core.messaging.SimpleMessageBus;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Basic implementation of an event store that persists
 * and retrieves from a Google App Engine Datastore 
 */
class AppEngineEventStore implements EventStore {

    /**
     * The name of the kind/schema
     */
    private static final String KIND = "EventStore";

    /**
     * Property name for the list of events in storage
     */
    private static final String EVENTS_PROPERTY = "Events";

    /**
     * Name of the task queue to publish events to
     */
    private String queue = null;

    /**
     * Constructor to specify the name of the task queue for
     * publishing event handlers to
     * 
     * @param queue
     */
    public AppEngineEventStore(String queue){
        this.queue = queue;
    }

    /**
     * Default constructor
     */
    public AppEngineEventStore(){
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveEvents(UUID aggregateId, int expectedVersion, Iterable<Event> events) throws EventCollisionException {

        if(events == null)
            return;

        Transaction transaction = null;

        try{

            DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
            transaction = dataStore.beginTransaction();

            Key key = KeyFactory.createKey(KIND, aggregateId.toString());		
            Entity entity = null;
            long currentVersion = 0;

            try {
                entity = dataStore.get(transaction,key);
            } catch (EntityNotFoundException e) {
                // Not a problem, just continue on. It is a new aggregate
            }

            List<String> entityEvents = null;

            if(entity == null){
                entity = new Entity(KIND, aggregateId.toString());
                entityEvents = new ArrayList<String>();
            }
            else{
                entityEvents = (List<String>)entity.getProperty(EVENTS_PROPERTY);
                currentVersion = entityEvents.size();

                //if the current version is different than what
                //was hydrated during the state change then we
                //know we have an event collision. This is a very simple approach
                //and more "business knowledge" can be added here to handle scenarios
                //where the versions may be different but the state change can still occur.
                if(currentVersion != expectedVersion)
                {
                    throw new EventCollisionException(aggregateId, expectedVersion);
                }
            }

            Gson gson = new Gson();

            //convert all of the new events to json for storage
            for(Event event : events){

                //increment the current version
                currentVersion++;

                String eventJson = gson.toJson(event);
                String kind = event.getClass().getName();

                EventModel newEvent = new EventModel(kind, eventJson, new Long(currentVersion));

                String json = gson.toJson(newEvent);
                entityEvents.add(json);
            }

            entity.setUnindexedProperty(EVENTS_PROPERTY, entityEvents);
            dataStore.put(entity);
            transaction.commit();

            /*
             * Publish the events using the message bus
             */
            publishEvents(events);

        } finally{
            if(transaction != null &&  transaction.isActive())
                transaction.rollback();
        }
    }

    /**
     * Publish all of the events in a single transaction.
     * 
     * @param events
     * @param dataStore
     */
    private void publishEvents(Iterable<Event> events){

        //Publish the event for listeners
        for(Event event : events){
            SimpleMessageBus.getInstance().publish(event, queue);
        }
    }

    /**
     * Loop through all of the events and deserialize the json into their respective types
     * 
     * @param entity
     * @return
     * @throws HydrationException
     */
    @SuppressWarnings("unchecked")
    private List<Event> hydrateEvents(Entity entity) throws HydrationException{

        Gson gson = new Gson();

        List<String> events = (List<String>)entity.getProperty(EVENTS_PROPERTY);
        List<Event> history = new ArrayList<Event>();

        for(String row : events){

            EventModel model = gson.fromJson(row, EventModel.class);

            try {

                Event event = (Event) gson.fromJson(model.getJson(), Class.forName(model.getKind()));
                history.add(event);

            } catch (JsonSyntaxException | ClassNotFoundException e) {
                /*
                 * Throw a hydration exception along with the aggregate Id and the message
                 */
                throw new HydrationException(UUID.fromString(entity.getKey().toString()),e.getMessage());
            }
        }

        return history;
    }

    @Override
    public Iterable<Event> getEvents(UUID aggregateId) throws HydrationException, AggregateNotFoundException {

    	if(aggregateId == null) throw new IllegalArgumentException("aggregateId is required");
    	
        DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

        Key key = KeyFactory.createKey(KIND, aggregateId.toString());		
        Entity entity = null;

        try {
            entity = dataStore.get(key);
        } catch (EntityNotFoundException e) {
            throw new AggregateNotFoundException(aggregateId);
        }

        return hydrateEvents(entity);
    }
}

