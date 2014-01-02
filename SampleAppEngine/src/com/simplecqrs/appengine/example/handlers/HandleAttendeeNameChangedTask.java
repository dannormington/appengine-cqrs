package com.simplecqrs.appengine.example.handlers;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.simplecqrs.appengine.example.domain.AttendeeNameChanged;
import com.simplecqrs.appengine.messaging.MessageLog;

/**
 * Deferred task for handling an attendee name change
 */
public class HandleAttendeeNameChangedTask implements DeferredTask{

	private static final long serialVersionUID = 1L;

	private AttendeeNameChanged event = null;
	
	public HandleAttendeeNameChangedTask(AttendeeNameChanged event){
		this.event = event;
	}
	
	@Override
	public void run() {
		MessageLog.log(String.format("Attendee %s name changed. Update your read models.", event.getAttendeeId().toString()));
	}
}
