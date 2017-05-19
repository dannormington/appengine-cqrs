package com.cqrs.appengine.core.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.messaging.Event;

/**
 * Base class for aggregate root implementations
 */
public abstract class AggregateRootBase implements AggregateRoot {

    /**
     * Aggregate id
     */
    protected UUID id = null;

    /**
     * list of changes that have occurred since last loaded
     */
    private List<Event> changes = new ArrayList<Event>();

    /**
     * returns the expected version
     */
    private int expectedVersion = 0;

    @Override
    public int getExpectedVersion(){
        return expectedVersion;
    }

    @Override
    public void markChangesAsCommitted(){
        changes.clear();
    } 

    @Override
    public UUID getId(){
        return id;
    }

    @Override
    public Iterable<Event> getUncommittedChanges(){
        return changes == null || changes.isEmpty() ? null : changes;
    }

    @Override
    public void loadFromHistory(Iterable<Event> history) throws HydrationException {

        if(history != null){
            for(Event event : history){
                applyChange(event, false);
                expectedVersion++;
            }
        }
    }

    /**
     * Apply the event assuming it is new
     * 
     * @param event
     * @throws HydrationException 
     */
    protected void applyChange(Event event) throws HydrationException {
        applyChange(event,true);
    }

    /**
     * Apply the change by invoking the inherited members apply method that fits the signature of the event passed
     * 
     * @param event
     * @param isNew
     * @throws HydrationException 
     */
    private void applyChange(Event event, boolean isNew) throws HydrationException {

        Method method = null;

        try {
            method = this.getClass().getDeclaredMethod("apply", event.getClass());
        } catch (NoSuchMethodException e) {
            //do nothing. This just means that the method signature wasn't found and
            //the aggregate doesn't need to apply any state changes since it wasn't
            //implemented.
        }

        if(method != null){
            method.setAccessible(true);
            try {
                method.invoke(this,event);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new HydrationException(this.getId(), e.getMessage());
            }		
        }

        if(isNew){
            changes.add(event);
        }
    }
}

