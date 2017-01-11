package com.cqrs.appengine.sample.commands;

import java.util.UUID;

import com.cqrs.appengine.core.messaging.Command;

/**
 * Command to confirm an email change request
 *
 */
public class ConfirmChangeEmail implements Command {

	private UUID attendeeId;
    private UUID confirmationId;
    
    
    /**
     * Default constructor for serialization
     */
    public ConfirmChangeEmail(){
    }
    
    /**
     * Constructor
     * 
     * @param attendeeId
     * @param confirmationId
     */
    public ConfirmChangeEmail(UUID attendeeId, UUID confirmationId){
    	this.attendeeId = attendeeId;
    	this.confirmationId = confirmationId;
    }
    

    /**
     * Get the confirmation id to verify the email change
     * 
     * @return
     */
    public UUID getConfirmationId(){
    	return confirmationId;
    }

    /**
     * Get the attendee Id
     * @return
     */
    public UUID getAttendeeId(){
        return attendeeId;
    }
}
