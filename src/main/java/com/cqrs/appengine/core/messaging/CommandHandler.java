package com.cqrs.appengine.core.messaging;

import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.exceptions.InvalidParametersException;

/**
 * Interface to handle particular command
 */
public interface CommandHandler<T extends Command> {

    /**
     * Handle the command
     * 
     * @param command
     * @throws EventCollisionException
     * @throws HydrationException
     * @throws AggregateNotFoundException
     * @throws InvalidParametersException
     */
    void handle(T command) throws EventCollisionException, HydrationException, AggregateNotFoundException, InvalidParametersException;
}

