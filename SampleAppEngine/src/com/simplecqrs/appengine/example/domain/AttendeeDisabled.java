package com.simplecqrs.appengine.example.domain;

import java.io.Serializable;
import java.util.UUID;

import com.simplecqrs.appengine.messaging.Event;

public class AttendeeDisabled implements Event, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID attendeeId;
	
	public AttendeeDisabled(UUID attendeeId){
		this.attendeeId = attendeeId;
	}
	
	public UUID getAttendeeId(){
		return attendeeId;
	}
}
