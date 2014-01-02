package com.simplecqrs.appengine.example.commands;

import java.util.UUID;

import com.simplecqrs.appengine.messaging.Command;

public class ChangeAttendeeNameCommand implements Command{
	private String firstName = null;
	private String lastName = null;
	private UUID attendeeId = null;
	
	/**
	 * Default constructor for serialization
	 */
	public ChangeAttendeeNameCommand(){
	}
	
	/**
	 * Constructor
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public ChangeAttendeeNameCommand(UUID attendeeId, String firstName, String lastName){
		this.attendeeId = attendeeId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public UUID getAttendeeId(){
		return attendeeId;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
}
