package com.simplecqrs.appengine.example.handlers.commands;

import com.simplecqrs.appengine.example.commands.ResolveDuplicateEmail;
import com.simplecqrs.appengine.example.domain.Attendee;
import com.simplecqrs.appengine.example.domain.DisableReason;
import com.simplecqrs.appengine.example.handlers.Constants;
import com.simplecqrs.appengine.messaging.CommandHandler;
import com.simplecqrs.appengine.messaging.MessageLog;
import com.simplecqrs.appengine.persistence.AggregateHydrationException;
import com.simplecqrs.appengine.persistence.EventCollisionException;
import com.simplecqrs.appengine.persistence.EventRepository;
import com.simplecqrs.appengine.persistence.Repository;

public class ResolveDuplicateEmailCommandHandler implements CommandHandler<ResolveDuplicateEmail>{

	@Override
	public void handle(ResolveDuplicateEmail command) throws EventCollisionException, AggregateHydrationException {
		
		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class, Constants.DOMAIN_EVENTS_PROCESSING_QUEUE);
		
		Attendee attendee = null;
		try {
			attendee = repository.getById(command.getAttendeeId());
		} catch (AggregateHydrationException e) {
			MessageLog.log(e);
			throw e;
			//in this situation you may want to publish an event to notify the administrator
		}
		
		if(attendee != null){
			
			attendee.disable(DisableReason.DUPLICATE);
			
			try {
				repository.save(attendee);
			} catch (EventCollisionException e) {
				MessageLog.log(e);
				throw e;
				//in this situation you may want to publish an event to the administrator
			}
		}
		
	}
}
