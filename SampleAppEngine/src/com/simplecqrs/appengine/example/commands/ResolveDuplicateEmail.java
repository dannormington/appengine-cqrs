package com.simplecqrs.appengine.example.commands;

import java.util.UUID;

import com.simplecqrs.appengine.messaging.Command;

/**
 * Command to resolve a duplicate email that has been
 * registered in the system
 */
public class ResolveDuplicateEmail implements Command{

    private UUID attendeeId;

    /**
     * Default constructor
     * 
     * @param attendeeId
     */
    public ResolveDuplicateEmail(UUID attendeeId){
        this.attendeeId = attendeeId;
    }

    /**
     * Get the attendee Id
     * @return
     */
    public UUID getAttendeeId(){
        return attendeeId;
    }
}
