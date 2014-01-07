package com.simplecqrs.appengine.example.handlers.commands;

import com.simplecqrs.appengine.example.commands.ChangeAttendeeName;
import com.simplecqrs.appengine.example.domain.Attendee;
import com.simplecqrs.appengine.example.handlers.Constants;
import com.simplecqrs.appengine.messaging.CommandHandler;
import com.simplecqrs.appengine.messaging.MessageLog;
import com.simplecqrs.appengine.persistence.AggregateHydrationException;
import com.simplecqrs.appengine.persistence.EventCollisionException;
import com.simplecqrs.appengine.persistence.EventRepository;
import com.simplecqrs.appengine.persistence.Repository;

public class ChangeAttendeeNameCommandHandler implements CommandHandler<ChangeAttendeeName> {

	@Override
	public void handle(ChangeAttendeeName command) throws EventCollisionException, AggregateHydrationException {
		
		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class, Constants.DOMAIN_EVENTS_PROCESSING_QUEUE);
		
		Attendee attendee = null;
		try {
			attendee = repository.getById(command.getAttendeeId());
		} catch (AggregateHydrationException e) {
			MessageLog.log(e);
			throw e;
			//in this situation you may want to publish an event to notify the user and/or administrators
		}
		
		if(attendee != null){
			try{
				attendee.changeName(command.getFirstName(), command.getLastName());
			}catch (IllegalArgumentException e){
				MessageLog.log(e);
				throw e;
			}
			
			try {
				repository.save(attendee);
			} catch (EventCollisionException e) {
				MessageLog.log(e);
				throw e;
				//in this situation you may want to publish an event to notify the user
			}
		}
		
	}

}
