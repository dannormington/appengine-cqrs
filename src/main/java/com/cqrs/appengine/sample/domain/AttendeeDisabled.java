package com.cqrs.appengine.sample.domain;

import java.io.Serializable;
import java.util.UUID;

import com.cqrs.appengine.core.messaging.Event;

/**
 * Event that is published when an attendee is disabled
 */
public class AttendeeDisabled implements Event, Serializable {

    private static final long serialVersionUID = 1L;

    private UUID attendeeId;
    private DisableReason reason;

    /**
     * Default constructor for serialization
     */
    public AttendeeDisabled(){}

    /**
     * Constructor
     * 
     * @param attendeeId
     * @param reason
     */
    public AttendeeDisabled(UUID attendeeId, DisableReason reason){
        this.attendeeId = attendeeId;
        this.reason = reason;
    }

    /**
     * Get the attendee Id
     * @return
     */
    public UUID getAttendeeId(){
        return attendeeId;
    }

    /**
     * Get the reason
     * 
     * @return
     */
    public DisableReason getReason(){
        return reason;
    }
}
