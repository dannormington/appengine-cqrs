package com.simplecqrs.appengine.messaging;

import com.google.appengine.api.taskqueue.DeferredTask;

/**
 * Base class for handling a specific event that will be published to a queue.
 * Ensure that all classes that extend this class implement a constructor
 * that receives a single parameter of the event's type
 * 
 * Code Example of required constructor:
 * <pre>
 * public class EmployeeDeactivatedEventHandler extends EventHandler{@code<EmployeeDeactivated>} {
 * 
 *   public EmployeeDeactivatedEventHandler(EmployeeDeactivated event) {
 *     super(event);
 *   }
 *   
 * }
 * </pre>
 * 
 * @param <T>
 * 
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
