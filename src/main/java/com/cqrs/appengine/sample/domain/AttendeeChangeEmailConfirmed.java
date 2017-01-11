package com.cqrs.appengine.sample.domain;

import java.io.Serializable;
import java.util.UUID;

import com.cqrs.appengine.core.messaging.Event;

/**
 * Event that is published when an attendee confirms their email change request
 *
 */
public class AttendeeChangeEmailConfirmed implements Event, Serializable {

    private static final long serialVersionUID = 1L;

    private UUID attendeeId;
    private UUID confirmationId;
    private String email;
    
    /**
     * Default constructor for serialization
     */
    public AttendeeChangeEmailConfirmed(){}
    
    /**
     * Constructor
     * 
     * @param attendeeId
     * @param confirmationId
     * @param email
     */
    public AttendeeChangeEmailConfirmed(UUID attendeeId, UUID confirmationId, String email){
    	this.attendeeId = attendeeId;
    	this.confirmationId = confirmationId;
    	this.email = email;
    }
    
    /**
     * Get the email
     * @return
     */
    public String getEmail(){
        return  email;
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
