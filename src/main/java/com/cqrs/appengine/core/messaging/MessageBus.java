package com.cqrs.appengine.core.messaging;

import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;

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
     * @throws EventCollisionException
     * @throws HydrationException
     * @throws AggregateNotFoundException
     * @throws InvalidParametersException
     */
    <T extends Command> void send(T command) throws EventCollisionException, HydrationException, AggregateNotFoundException;
}