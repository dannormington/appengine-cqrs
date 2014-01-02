package com.simplecqrs.appengine.example.handlers;

import com.google.common.eventbus.Subscribe;
import com.simplecqrs.appengine.example.domain.AttendeeNameChanged;
import com.simplecqrs.appengine.example.domain.AttendeeRegistered;
import com.simplecqrs.appengine.messaging.MessageLog;

public class EventHandlers {
	
	@Subscribe
	public void handle(AttendeeRegistered event){
		MessageLog.log(String.format("attendee %s registered.", event.getAttendeeId().toString()));
	}
	
	@Subscribe
	public void handle(AttendeeNameChanged event){
		MessageLog.log(String.format("attendee %s changed name.", event.getAttendeeId().toString()));
	}
}
