package com.simplecqrs.appengine.example.handlers;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.simplecqrs.appengine.example.domain.AttendeeRegistered;
import com.simplecqrs.appengine.messaging.MessageLog;

/**
 * Deferred task to handle an attendee registering
 */
public class HandleAttendeeRegisteredTask implements DeferredTask{

	private static final long serialVersionUID = 1L;

	private AttendeeRegistered event = null;
	
	public HandleAttendeeRegisteredTask(AttendeeRegistered event){
		this.event = event;
	}
	
	@Override
	public void run() {
		MessageLog.log(String.format("Attendee %s registered. Update your read models.", event.getAttendeeId().toString()));
	}
}
