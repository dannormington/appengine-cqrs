package com.cqrs.appengine.sample.handlers.events;

import com.cqrs.appengine.core.messaging.EventHandler;
import com.cqrs.appengine.sample.MessageLog;
import com.cqrs.appengine.sample.domain.AttendeeDisabled;

public class AttendeeDisabledEventHandler extends EventHandler<AttendeeDisabled> {

    private static final long serialVersionUID = 1L;

    public AttendeeDisabledEventHandler(AttendeeDisabled event) {
        super(event);
    }

    @Override
    public void run() {

        MessageLog.log(String.format("Attendee %s Disabled.", event.getAttendeeId().toString()));

        //here is where you may send an email to the attendee notifying them that they have been disabled.
    }
}
