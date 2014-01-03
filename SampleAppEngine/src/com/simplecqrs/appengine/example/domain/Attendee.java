package com.simplecqrs.appengine.example.domain;

import java.util.UUID;

import com.simplecqrs.appengine.domain.AggregateRootBase;

public class Attendee extends AggregateRootBase {
	
	public Attendee(UUID attendeeId){
		super(attendeeId);
	}
	
	private Attendee(UUID attendeeId, String email, String firstName, String lastName)  throws IllegalArgumentException {
		this(attendeeId);

		applyChange(new AttendeeRegistered(attendeeId, email, firstName, lastName));
	}
	
	public void changeName(String firstName, String lastName) throws IllegalArgumentException {
		
		//perform validation checks here before calling applyChange
		if(firstName != null && lastName != null)
			applyChange(new AttendeeNameChanged(this.getId(), firstName, lastName));
		else
			throw new IllegalArgumentException();
	}
	
	public void disable(){
		applyChange(new AttendeeDisabled(this.getId()));
	}
	
	public static Attendee create(UUID attendeeId, String email, String firstName, String lastName){
	
		if(email != null && firstName != null && lastName != null)
			return new Attendee(attendeeId, email, firstName, lastName);
		
		return null;
	}
}
