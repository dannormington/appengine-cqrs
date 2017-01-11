package com.cqrs.appengine.sample.commands;

import java.util.UUID;

import com.cqrs.appengine.core.messaging.Command;

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
    }

    /**
     * Constructor
     * 
     * @param firstName
     * @param lastName
     */
    public RegisterAttendee(UUID attendeeId, String email, String firstName, String lastName){
        this.attendeeId = attendeeId;
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
