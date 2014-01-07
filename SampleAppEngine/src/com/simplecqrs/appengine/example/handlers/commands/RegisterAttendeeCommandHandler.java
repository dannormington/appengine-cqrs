package com.simplecqrs.appengine.example.handlers.commands;

import com.simplecqrs.appengine.example.commands.RegisterAttendee;
import com.simplecqrs.appengine.example.domain.Attendee;
import com.simplecqrs.appengine.example.handlers.Constants;
import com.simplecqrs.appengine.messaging.CommandHandler;
import com.simplecqrs.appengine.messaging.MessageLog;
import com.simplecqrs.appengine.persistence.EventCollisionException;
import com.simplecqrs.appengine.persistence.EventRepository;
import com.simplecqrs.appengine.persistence.Repository;

public class RegisterAttendeeCommandHandler implements CommandHandler<RegisterAttendee> {

	public void handle(RegisterAttendee command) throws EventCollisionException {
			
		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class, Constants.DOMAIN_EVENTS_PROCESSING_QUEUE);
		
		Attendee attendee = Attendee.create(command.getAttendeeId(), command.getEmail(), command.getFirstName(), command.getLastName());
		
		if(attendee != null){
			try {
				repository.save(attendee);
			} catch (EventCollisionException e) {
				//This exception shouldn't occur since it is a new attendee
				MessageLog.log(e);
				throw e;
			}
		}
	}
}
