package com.simplecqrs.appengine.example.domain;

import java.util.UUID;

import com.simplecqrs.appengine.messaging.Event;

public class AttendeeNameChanged implements Event{
	private UUID attendeeId;
	private String firstName;
	private String lastName;
	
	public AttendeeNameChanged(){}
	
	public AttendeeNameChanged(UUID attendeeId, String firstName, String lastName){
		this.attendeeId = attendeeId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFirstName(){
		return  firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public UUID getAttendeeId(){
		return attendeeId;
	}
}
