package com.simplecqrs.appengine.example.domain;

import java.io.Serializable;
import java.util.UUID;

import com.simplecqrs.appengine.messaging.Event;

public class AttendeeRegistered implements Event, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID attendeeId;
	private String email;
	private String firstName;
	private String lastName;
	
	public AttendeeRegistered(){}
	
	public AttendeeRegistered(UUID attendeeId, String email, String firstName, String lastName){
		this.attendeeId = attendeeId;
		this.email = email;
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
	
	public String getEmail(){
		return email;
	}
}
