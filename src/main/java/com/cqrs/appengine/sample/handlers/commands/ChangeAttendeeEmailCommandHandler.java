package com.cqrs.appengine.sample.handlers.commands;

import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.messaging.CommandHandler;
import com.cqrs.appengine.core.persistence.EventRepository;
import com.cqrs.appengine.core.persistence.Repository;
import com.cqrs.appengine.sample.commands.ChangeAttendeeEmail;
import com.cqrs.appengine.sample.domain.Attendee;
import com.cqrs.appengine.sample.handlers.Constants;

public class ChangeAttendeeEmailCommandHandler implements CommandHandler<ChangeAttendeeEmail> {

	@Override
	public void handle(ChangeAttendeeEmail command) throws EventCollisionException, HydrationException, AggregateNotFoundException {

		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class, Constants.DOMAIN_EVENTS_PROCESSING_QUEUE);

        Attendee attendee = repository.getById(command.getAttendeeId());
        attendee.changeEmail(command.getEmail());
        repository.save(attendee);
		
	}

}
