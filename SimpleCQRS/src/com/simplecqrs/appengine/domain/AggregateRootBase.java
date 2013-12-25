package com.simplecqrs.appengine.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.simplecqrs.appengine.messaging.Event;

/**
 * Base class for aggregate root implementations
 */
public abstract class AggregateRootBase implements AggregateRoot {

	/**
	 * Aggregate id
	 */
	private UUID id = null;

	/**
	 * list of changes that have occurred since last loaded
	 */
	private List<Event> changes = new ArrayList<Event>();
	
	/**
	 * returns the expected version
	 */
	private int expectedVersion = 0;
	
	/**
	 * Default constructor
	 */
	public AggregateRootBase(){
		id = UUID.randomUUID();
	}
	
	/**
	 * Constructor to initialize the aggregate's id
	 * @param id
	 */
	public AggregateRootBase(UUID id){
		this.id = id;
	}
	
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
	public void loadFromHistory(Iterable<Event> history) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(history != null){
			for(Event event : history){
				applyChange(event, false);
			}
		}
	}
	
	/**
	 * Apply the event assuming it is new
	 * 
	 * @param event
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	protected void applyChange(Event event) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		applyChange(event,true);
	}
	
	/**
	 * Apply the change by invoking the inherited members apply method that fits the signature of the event passed
	 * 
	 * @param event
	 * @param isNew
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private void applyChange(Event event, boolean isNew) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Method method = this.getClass().getDeclaredMethod("apply", event.getClass());
		method.setAccessible(true);
		method.invoke(this,event);
		
		expectedVersion++;
			
		if(isNew){
			changes.add(event);
		}
	}
}
