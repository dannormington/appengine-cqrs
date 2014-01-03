package com.simplecqrs.appengine.messaging;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
 * Simple wrapper around guava eventbus for handling commands and event publishing
 */
public class MessageBus{
	
	private AsyncEventBus asyncEventBus;
	private EventBus commandBus;
	
	private MessageBus(EventBus eventBus, AsyncEventBus asyncEventBus){
		this.commandBus = eventBus;
		this.asyncEventBus = asyncEventBus;
	}
	
	/**
	 * Get an instance of the message bus
	 * @return
	 */
	public static MessageBus getInstance(){
		return InstanceHolder.INSTANCE;
	}
	
	/**
	 * Register commands
	 * 
	 * @param object
	 */
	public void registerCommands(Object object){
		commandBus.register(object);
	}
	
	/**
	 * Register events
	 * 
	 * @param object
	 */
	public void registerEvents(Object object){
		asyncEventBus.register(object);
	}
	
	/**
	 * Publish events asynchronously
	 * 
	 * @param event
	 */
	public void publish(Event event){
		asyncEventBus.post(event);
	}
	
	/**
	 * Process the command synchronously
	 * 
	 * @param command
	 */
	public void send(Command command){
		commandBus.post(command);
	}
	
	/**
	 * Create the message bus
	 * 
	 * @return
	 */
	private static MessageBus create(){
		AsyncEventBus asyncEventBus = new AsyncEventBus(new ThreadExecutor());
		return new MessageBus(new EventBus(),asyncEventBus);
	}
	
	private static class InstanceHolder{
		public static final MessageBus INSTANCE = MessageBus.create();
	}
}