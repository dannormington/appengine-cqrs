package com.simplecqrs.appengine.example.commands;

import java.util.UUID;

import com.simplecqrs.appengine.messaging.Command;

/**
 * Command to change an attendee's name
 */
public class ChangeAttendeeName implements Command{
    private String firstName = null;
    private String lastName = null;
    private UUID attendeeId = null;

    /**
     * Default constructor for serialization
     */
    public ChangeAttendeeName(){
    }

    /**
     * Constructor
     * 
     * @param firstName
     * @param lastName
     */
    public ChangeAttendeeName(UUID attendeeId, String firstName, String lastName){
        this.attendeeId = attendeeId;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
