package com.cqrs.appengine.sample.domain;

import java.io.Serializable;
import java.util.UUID;

import com.cqrs.appengine.core.messaging.Event;

/**
 * Event that is published when an attendee changes their email
 *
 */
public class AttendeeEmailChanged implements Event, Serializable {

	private static final long serialVersionUID = 1L;

	private UUID attendeeId;
	private UUID confirmationId;
    private String email;

    /**
     * Default constructor for serialization
     */
    public AttendeeEmailChanged(){}

    /**
     * Constructor
     * 
     * @param attendeeId
     * @param firstName
     * @param lastName
     */
    public AttendeeEmailChanged(UUID attendeeId, String email){
        this.attendeeId = attendeeId;
        this.email = email;
        this.confirmationId = UUID.randomUUID();
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
