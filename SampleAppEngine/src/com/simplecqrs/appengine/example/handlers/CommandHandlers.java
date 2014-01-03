package com.simplecqrs.appengine.example.handlers;

import com.google.common.eventbus.Subscribe;
import com.simplecqrs.appengine.example.commands.ChangeAttendeeName;
import com.simplecqrs.appengine.example.commands.RegisterAttendee;
import com.simplecqrs.appengine.example.commands.ResolveDuplicateEmail;
import com.simplecqrs.appengine.example.domain.Attendee;
import com.simplecqrs.appengine.example.domain.DisableReason;
import com.simplecqrs.appengine.messaging.MessageLog;
import com.simplecqrs.appengine.persistence.AggregateHydrationException;
import com.simplecqrs.appengine.persistence.EventCollisionException;
import com.simplecqrs.appengine.persistence.EventRepository;
import com.simplecqrs.appengine.persistence.Repository;

/**
 * Class that handles all commands
 */
public class CommandHandlers {
	
	/**
	 * Instance of a repository
	 */
	Repository<Attendee> repository = null;
	
	/**
	 * Default constructor
	 */
	public CommandHandlers(){
		repository = new EventRepository<Attendee>(Attendee.class);
	}

	/**
	 * Handle the {@link RegisterAttendee} command
	 * 
	 * @param command
	 */
	@Subscribe
	public void handle(RegisterAttendee command) {
		
		Attendee attendee = Attendee.create(command.getAttendeeId(), command.getEmail(), command.getFirstName(), command.getLastName());
		
		if(attendee != null){
			try {
				repository.save(attendee);
			} catch (EventCollisionException e) {
				//This exception shouldn't occur since it is a new attendee
				MessageLog.log(e);
			}
		}
	}
	
	/**
	 * Handle the {@link ChangeAttendeeName} command
	 * @param command
	 */
	@Subscribe
	public void handle(ChangeAttendeeName command) {
		
		Attendee attendee = null;
		try {
			attendee = repository.getById(command.getAttendeeId());
		} catch (AggregateHydrationException e) {
			MessageLog.log(e);
			//in this situation you may want to publish an event to notify the user and/or administrators
		}
		
		if(attendee != null){
			try{
				attendee.changeName(command.getFirstName(), command.getLastName());
			}catch (IllegalArgumentException e){
				MessageLog.log(e);
				return;
			}
			
			try {
				repository.save(attendee);
			} catch (EventCollisionException e) {
				MessageLog.log(e);
				//in this situation you may want to publish an event to notify the user
			}
		}
	}
	
	/**
	 * Handle the {@link ResolveDuplicateEmail} command
	 * @param command
	 */
	@Subscribe
	public void handle(ResolveDuplicateEmail command){
		Attendee attendee = null;
		try {
			attendee = repository.getById(command.getAttendeeId());
		} catch (AggregateHydrationException e) {
			MessageLog.log(e);
			//in this situation you may want to publish an event to notify the administrator
		}
		
		if(attendee != null){
			
			attendee.disable(DisableReason.DUPLICATE);
			
			try {
				repository.save(attendee);
			} catch (EventCollisionException e) {
				MessageLog.log(e);
				//in this situation you may want to publish an event to the administrator
			}
		}
	}
}
