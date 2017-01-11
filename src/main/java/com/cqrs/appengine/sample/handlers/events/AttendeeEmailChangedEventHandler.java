package com.cqrs.appengine.sample.handlers.events;

import com.cqrs.appengine.core.messaging.EventHandler;
import com.cqrs.appengine.sample.MessageLog;
import com.cqrs.appengine.sample.domain.AttendeeEmailChanged;

public class AttendeeEmailChangedEventHandler extends EventHandler<AttendeeEmailChanged>{

	private static final long serialVersionUID = 1L;

	public AttendeeEmailChangedEventHandler(AttendeeEmailChanged event) {
		super(event);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void run() {

        MessageLog.log(String.format("Attendee Id %s requested to change email address to : %s.", event.getAttendeeId().toString(), event.getEmail()));

        //here is where you may send an email to the attendee with a link to "confirm" the change.
    }
}
