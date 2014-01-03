package com.simplecqrs.appengine.example.commands;

import java.util.UUID;

import com.simplecqrs.appengine.messaging.Command;

/**
 * Simple command to register an attendee
 */
public class RegisterAttendee implements Command {
	
	private String email = null;
	private String firstName = null;
	private String lastName = null;
	private UUID attendeeId = null;
	
	/**
	 * Default constructor for serialization
	 */
	public RegisterAttendee(){
		this.attendeeId = UUID.randomUUID();
	}
	
	/**
	 * Constructor
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public RegisterAttendee(String email, String firstName, String lastName){
		this();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * Get the attendee Id
	 * 
	 * @return
	 */
	public UUID getAttendeeId(){
		return attendeeId;
	}
	
	/**
	 * Get the first name
	 * 
	 * @return
	 */
	public String getFirstName(){
		return firstName;
	}
	
	/**
	 * Get the last name
	 * 
	 * @return
	 */
	public String getLastName(){
		return lastName;
	}
	
	/**
	 * Get the email
	 * @return
	 */
	public String getEmail(){
		return email;
	}
}
