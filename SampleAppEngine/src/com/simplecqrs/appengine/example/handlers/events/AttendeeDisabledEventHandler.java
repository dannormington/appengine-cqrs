package com.simplecqrs.appengine.example.handlers.events;

import com.simplecqrs.appengine.example.domain.AttendeeDisabled;
import com.simplecqrs.appengine.messaging.EventHandler;
import com.simplecqrs.appengine.example.MessageLog;

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
