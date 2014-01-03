package com.simplecqrs.appengine.example.commands;

import java.util.UUID;

import com.simplecqrs.appengine.messaging.Command;

public class ResolveDuplicateEmail implements Command{

	private UUID attendeeId;
	
	public ResolveDuplicateEmail(UUID attendeeId){
		this.attendeeId = attendeeId;
	}
	
	public UUID getAttendeeId(){
		return attendeeId;
	}
}
