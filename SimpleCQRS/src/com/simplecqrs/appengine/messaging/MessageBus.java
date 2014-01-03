package com.simplecqrs.appengine.messaging;

import com.google.common.eventbus.AsyncEventBus;

/**
 * Simple wrapper around guava eventbus for handling commands and event publishing
 */
public class MessageBus{
	
	private AsyncEventBus eventBus;
	
	private MessageBus(AsyncEventBus eventBus){
		this.eventBus = eventBus;
	}
	
	/**
	 * Get an instance of the message bus
	 * @return
	 */
	public static MessageBus getInstance(){
		return InstanceHolder.INSTANCE;
	}
	
	/**
	 * Register events/commands
	 * 
	 * @param object
	 */
	public void register(Object object){
		eventBus.register(object);
	}
	
	/**
	 * Publish events asynchronously
	 * 
	 * @param event
	 */
	public void publish(Event event){
		eventBus.post(event);
	}
	
	/**
	 * Process the command asynchronously
	 * 
	 * @param command
	 */
	public void send(Command command){
		eventBus.post(command);
	}
	
	/**
	 * Create the message bus
	 * 
	 * @return
	 */
	private static MessageBus create(){
		return new MessageBus(new AsyncEventBus(new ThreadExecutor()));
	}
	
	private static class InstanceHolder{
		public static final MessageBus INSTANCE = MessageBus.create();
	}
}