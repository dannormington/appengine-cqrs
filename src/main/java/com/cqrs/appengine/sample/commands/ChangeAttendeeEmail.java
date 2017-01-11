package com.cqrs.appengine.sample.commands;

import java.util.UUID;

import com.cqrs.appengine.core.messaging.Command;

/**
 * Command to change an Attendee's email address
 *
 */
public class ChangeAttendeeEmail implements Command {

	private UUID attendeeId = null;
	private String email = null;
    
    /**
     * Default constructor for serialization
     */
    public ChangeAttendeeEmail(){
    }

    /**
     * Constructor
     * 
     * @param firstName
     * @param lastName
     */
    public ChangeAttendeeEmail(UUID attendeeId, String email){
        this.attendeeId = attendeeId;
        this.email = email;
    }

    /**
     * Get the attendee the Id
     * 
     * @return
     */
    public UUID getAttendeeId(){
        return attendeeId;
    }

    /**
     * Get the email
     * 
     * @return
     */
    public String getEmail(){
        return email;
    }  
}
