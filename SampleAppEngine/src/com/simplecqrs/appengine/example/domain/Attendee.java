package com.simplecqrs.appengine.example.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import com.simplecqrs.appengine.domain.AggregateRootBase;

public class Attendee extends AggregateRootBase {
	
	//private String firstName = null;
	//private String lastName = null;
	
	public Attendee(UUID attendeeId){
		super(attendeeId);
	}
	
	public Attendee(UUID attendeeId, String firstName, String lastName){
		this(attendeeId);
		try {
			applyChange(new AttendeeRegistered(attendeeId, firstName, lastName));
		} catch (NoSuchMethodException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void apply(AttendeeRegistered event){
		//firstName = event.getFirstName();
		//lastName = event.getLastName();
	}
}
