package com.simplecqrs.appengine.messaging;

import com.google.appengine.api.taskqueue.DeferredTask;

/**
 * Class for handling a specific event that will be published to a queue
 * 
 * @param <T>
 */
@SuppressWarnings("serial")
public abstract class EventHandler<T extends Event> implements DeferredTask {
	
	/**
	 * The event
	 */
	protected T event;
	
	/**
	 * Default constructor
	 * 
	 * @param event
	 */
	public EventHandler(T event){
		this.event = event;
	}
}
