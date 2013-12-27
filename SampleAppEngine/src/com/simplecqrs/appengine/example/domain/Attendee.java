package com.simplecqrs.appengine.example.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import com.simplecqrs.appengine.domain.AggregateRootBase;
import com.simplecqrs.appengine.messaging.MessageLog;

public class Attendee extends AggregateRootBase {
	
	public Attendee(UUID attendeeId){
		super(attendeeId);
	}
	
	public Attendee(UUID attendeeId, String firstName, String lastName) {
		this(attendeeId);
		try {
			//perform validation checks here
			applyChange(new AttendeeRegistered(attendeeId, firstName, lastName));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			MessageLog.log(e);
		}
	}
	
	public void changeName(String firstName, String lastName){
		
		//perform validation checks here
		
		try {
			applyChange(new AttendeeNameChanged(this.getId(), firstName, lastName));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			MessageLog.log(e);
		}
	}
}
