package com.simplecqrs.appengine.example.handlers;

import com.google.common.eventbus.Subscribe;
import com.simplecqrs.appengine.example.ChangeAttendeeNameCommand;
import com.simplecqrs.appengine.example.RegisterAttendeeCommand;
import com.simplecqrs.appengine.example.domain.Attendee;
import com.simplecqrs.appengine.messaging.MessageLog;
import com.simplecqrs.appengine.persistence.AggregateHydrationException;
import com.simplecqrs.appengine.persistence.EventCollisionException;
import com.simplecqrs.appengine.persistence.EventRepository;
import com.simplecqrs.appengine.persistence.Repository;

public class CommandHandlers {
	
	Repository<Attendee> repository = null;
	
	public CommandHandlers(){
		repository = new EventRepository<Attendee>(Attendee.class);
	}

	@Subscribe
	public void handle(RegisterAttendeeCommand command) throws Exception {
		
		Attendee attendee = null;
		try {
			attendee = repository.getById(command.getAttendeeId());
		} catch (AggregateHydrationException e) {
			MessageLog.log(e);
			throw e;
		}
		
		if(attendee == null){
			attendee = new Attendee(command.getAttendeeId(), command.getFirstName(), command.getLastName());
			try {
				repository.save(attendee);
			} catch (EventCollisionException e) {
				MessageLog.log(e);
				throw e;
			}
		}
	}
	
	@Subscribe
	public void handle(ChangeAttendeeNameCommand command) throws Exception {
		
		Attendee attendee = null;
		try {
			attendee = repository.getById(command.getAttendeeId());
		} catch (AggregateHydrationException e) {
			MessageLog.log(e);
			throw e;
		}
		
		if(attendee != null){
			attendee.changeName(command.getFirstName(), command.getLastName());
			try {
				repository.save(attendee);
			} catch (EventCollisionException e) {
				MessageLog.log(e);
				throw e;
			}
		}
	}
}
