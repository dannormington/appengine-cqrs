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
	
	public static MessageBus getInstance(){
		return InstanceHolder.INSTANCE;
	}
	
	public void registerCommands(Object object){
		commandBus.register(object);
	}
	
	public void registerEvents(Object object){
		asyncEventBus.register(object);
	}
	
	public void publish(Event event){
		asyncEventBus.post(event);
	}
	
	public void send(Command command){
		commandBus.post(command);
	}
	
	private static MessageBus create(){
		AsyncEventBus asyncEventBus = new AsyncEventBus(new ThreadExecutor());
		return new MessageBus(new EventBus(),asyncEventBus);
	}
	
	private static class InstanceHolder{
		public static final MessageBus INSTANCE = MessageBus.create();
	}
}