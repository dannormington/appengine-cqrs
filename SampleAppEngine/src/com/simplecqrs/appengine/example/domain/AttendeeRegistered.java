package com.simplecqrs.appengine.example.domain;

import java.io.Serializable;
import java.util.UUID;

import com.simplecqrs.appengine.messaging.Event;

/**
 * Event that  is published when an attendee registers
 */
public class AttendeeRegistered implements Event, Serializable {

    private static final long serialVersionUID = 1L;

    private UUID attendeeId;
    private String email;
    private String firstName;
    private String lastName;

    /**
     * Constructor for serialization
     */
    public AttendeeRegistered(){}

    /**
     * Constructor
     * 
     * @param attendeeId
     * @param email
     * @param firstName
     * @param lastName
     */
    public AttendeeRegistered(UUID attendeeId, String email, String firstName, String lastName){
        this.attendeeId = attendeeId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Get the first name
     * 
     * @return
     */
    public String getFirstName(){
        return  firstName;
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
     * Get the attendee Id
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
