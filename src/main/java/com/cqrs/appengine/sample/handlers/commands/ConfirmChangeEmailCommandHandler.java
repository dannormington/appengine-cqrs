package com.cqrs.appengine.sample.handlers.commands;

import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.messaging.CommandHandler;
import com.cqrs.appengine.core.persistence.EventRepository;
import com.cqrs.appengine.core.persistence.Repository;
import com.cqrs.appengine.sample.commands.ConfirmChangeEmail;
import com.cqrs.appengine.sample.domain.Attendee;
import com.cqrs.appengine.sample.handlers.Constants;

public class ConfirmChangeEmailCommandHandler implements CommandHandler<ConfirmChangeEmail>{

	@Override
	public void handle(ConfirmChangeEmail command) throws EventCollisionException, HydrationException, AggregateNotFoundException {
		
		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class, Constants.DOMAIN_EVENTS_PROCESSING_QUEUE);

        Attendee attendee = repository.getById(command.getAttendeeId());
        attendee.confirmChangeEmail(command.getConfirmationId());
        repository.save(attendee);
		
	}

}
