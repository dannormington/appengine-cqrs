package com.simplecqrs.appengine.example.handlers.commands;

import com.simplecqrs.appengine.example.commands.ResolveDuplicateEmail;
import com.simplecqrs.appengine.example.domain.Attendee;
import com.simplecqrs.appengine.example.domain.DisableReason;
import com.simplecqrs.appengine.example.handlers.Constants;
import com.simplecqrs.appengine.messaging.CommandHandler;
import com.simplecqrs.appengine.exceptions.HydrationException;
import com.simplecqrs.appengine.exceptions.EventCollisionException;
import com.simplecqrs.appengine.persistence.EventRepository;
import com.simplecqrs.appengine.persistence.Repository;

public class ResolveDuplicateEmailCommandHandler implements CommandHandler<ResolveDuplicateEmail>{

	@Override
	public void handle(ResolveDuplicateEmail command) throws EventCollisionException, HydrationException {
		
		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class, Constants.DOMAIN_EVENTS_PROCESSING_QUEUE);
		
		Attendee attendee = repository.getById(command.getAttendeeId());
		
		if(attendee != null){
			attendee.disable(DisableReason.DUPLICATE);
			repository.save(attendee);
		}
	}
}
