package com.simplecqrs.appengine.example.handlers;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.simplecqrs.appengine.example.domain.AttendeeDisabled;
import com.simplecqrs.appengine.messaging.MessageLog;

public class HandleAttendeeDisabledTask implements DeferredTask {
	
	private static final long serialVersionUID = 1L;

	private AttendeeDisabled event = null;
	
	public HandleAttendeeDisabledTask(AttendeeDisabled event){
		this.event = event;
	}
	
	@Override
	public void run() {
		
		MessageLog.log(String.format("Attendee %s Disabled.", event.getAttendeeId().toString()));
		
		//here is where you may send an email to the attendee notifying them that they have been disabled.
	}
}
