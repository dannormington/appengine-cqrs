package com.simplecqrs.appengine.example.domain;

import java.io.Serializable;
import java.util.UUID;

import com.simplecqrs.appengine.messaging.Event;

/**
 * Event that is published when an attendee changes their name
 */
public class AttendeeNameChanged implements Event, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private UUID attendeeId;
	private String firstName;
	private String lastName;
	
	/**
	 * Default constructor for serialization
	 */
	public AttendeeNameChanged(){}
	
	/**
	 * Constructor
	 * 
	 * @param attendeeId
	 * @param firstName
	 * @param lastName
	 */
	public AttendeeNameChanged(UUID attendeeId, String firstName, String lastName){
		this.attendeeId = attendeeId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * Get the first name
	 * @return
	 */
	public String getFirstName(){
		return  firstName;
	}
	
	/**
	 * Get the last name
	 * @return
	 */
	public String getLastName(){
		return lastName;
	}
	
	/**
	 * Get the attendee Id
	 * @return
	 */
	public UUID getAttendeeId(){
		return attendeeId;
	}
}
