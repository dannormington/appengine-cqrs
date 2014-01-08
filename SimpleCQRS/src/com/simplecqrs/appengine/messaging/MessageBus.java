package com.simplecqrs.appengine.messaging;

import com.simplecqrs.appengine.exceptions.EventCollisionException;
import com.simplecqrs.appengine.exceptions.HydrationException;

/**
 * Interface for a simple message bus
 */
public interface MessageBus{

	/**
	 * Register a command handler
	 * 
	 * @param aClass
	 * @param handler
	 */
	<T extends Command> void registerCommandHandler(Class<T> aClass, CommandHandler<T> handler);
	
	/**
	 * Register an event handler
	 * 
	 * @param aClass
	 * @param handler
	 */
	<T extends Event, H extends EventHandler<T>> void registerEventHandler(Class<T> aClass, Class<H> handler);
	
	/**
	 * Publish an event to the default queue
	 * 
	 * @param event
	 */
	<T extends Event> void publish(T event);
	
	/**
	 * Publish an event to the specified queue
	 * 
	 * @param event
	 * @param queue
	 */
	<T extends Event> void publish(T event, String queue);
	
	/**
	 * Execute a command
	 * 
	 * @param command
	 * @throws AggregateHydrationException 
	 * @throws EventCollisionException 
	 */
	<T extends Command> void send(T command) throws EventCollisionException, HydrationException;
}