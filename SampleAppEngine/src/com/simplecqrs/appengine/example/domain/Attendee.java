package com.simplecqrs.appengine.example.domain;

import java.util.UUID;

import com.simplecqrs.appengine.domain.AggregateRootBase;

public class Attendee extends AggregateRootBase {
	
	public Attendee(UUID attendeeId){
		super(attendeeId);
	}
	
	public Attendee(UUID attendeeId, String firstName, String lastName) {
		this(attendeeId);
		
		//perform validation checks here
		applyChange(new AttendeeRegistered(attendeeId, firstName, lastName));
	}
	
	public void changeName(String firstName, String lastName){
		
		//perform validation checks here
		applyChange(new AttendeeNameChanged(this.getId(), firstName, lastName));
	}
}
