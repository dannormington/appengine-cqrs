package com.cqrs.appengine.sample.handlers.commands;

import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.messaging.CommandHandler;
import com.cqrs.appengine.core.persistence.EventRepository;
import com.cqrs.appengine.core.persistence.Repository;
import com.cqrs.appengine.sample.commands.RegisterAttendee;
import com.cqrs.appengine.sample.domain.Attendee;
import com.cqrs.appengine.sample.handlers.Constants;

public class RegisterAttendeeCommandHandler implements CommandHandler<RegisterAttendee> {

    @Override
    public void handle(RegisterAttendee command) throws EventCollisionException, HydrationException {

        Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class, Constants.DOMAIN_EVENTS_PROCESSING_QUEUE);

        Attendee attendee = null;
        
		try {
			attendee = repository.getById(command.getAttendeeId());
		} catch (AggregateNotFoundException e) {
			//Do nothing. If it isn't found continue to process
		}
        
        //if the aggregate Id already exists throw an exception
        if(attendee != null) throw new EventCollisionException(command.getAttendeeId(), 0, "Attendee Id already exists.");
        
        attendee = Attendee.create(command.getAttendeeId(), command.getEmail(), command.getFirstName(), command.getLastName());
        repository.save(attendee);
        
    }
}
