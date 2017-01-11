package com.cqrs.appengine.sample;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cqrs.appengine.core.messaging.SimpleMessageBus;
import com.cqrs.appengine.sample.commands.ChangeAttendeeEmail;
import com.cqrs.appengine.sample.commands.ChangeAttendeeName;
import com.cqrs.appengine.sample.commands.ConfirmChangeEmail;
import com.cqrs.appengine.sample.commands.RegisterAttendee;
import com.cqrs.appengine.sample.commands.ResolveDuplicateEmail;
import com.cqrs.appengine.sample.domain.AttendeeChangeEmailConfirmed;
import com.cqrs.appengine.sample.domain.AttendeeDisabled;
import com.cqrs.appengine.sample.domain.AttendeeEmailChanged;
import com.cqrs.appengine.sample.domain.AttendeeNameChanged;
import com.cqrs.appengine.sample.domain.AttendeeRegistered;
import com.cqrs.appengine.sample.handlers.commands.ChangeAttendeeEmailCommandHandler;
import com.cqrs.appengine.sample.handlers.commands.ChangeAttendeeNameCommandHandler;
import com.cqrs.appengine.sample.handlers.commands.ConfirmChangeEmailCommandHandler;
import com.cqrs.appengine.sample.handlers.commands.RegisterAttendeeCommandHandler;
import com.cqrs.appengine.sample.handlers.commands.ResolveDuplicateEmailCommandHandler;
import com.cqrs.appengine.sample.handlers.events.AttendeeChangeEmailConfirmedEventHandler;
import com.cqrs.appengine.sample.handlers.events.AttendeeDisabledEventHandler;
import com.cqrs.appengine.sample.handlers.events.AttendeeEmailChangedEventHandler;
import com.cqrs.appengine.sample.handlers.events.AttendeeNameChangedEventHandler;
import com.cqrs.appengine.sample.handlers.events.AttendeeRegisteredEventHandler;

/**
 * Custom listener to handle command/event registration with the message
 * bus during warm-up
 */
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // App Engine does not currently invoke this method.	
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {

        SimpleMessageBus.getInstance().registerCommandHandler(RegisterAttendee.class, new RegisterAttendeeCommandHandler());
        SimpleMessageBus.getInstance().registerCommandHandler(ChangeAttendeeName.class, new ChangeAttendeeNameCommandHandler());
        SimpleMessageBus.getInstance().registerCommandHandler(ResolveDuplicateEmail.class, new ResolveDuplicateEmailCommandHandler());
        SimpleMessageBus.getInstance().registerCommandHandler(ChangeAttendeeEmail.class, new ChangeAttendeeEmailCommandHandler());
        SimpleMessageBus.getInstance().registerCommandHandler(ConfirmChangeEmail.class, new ConfirmChangeEmailCommandHandler());

        SimpleMessageBus.getInstance().registerEventHandler(AttendeeRegistered.class, AttendeeRegisteredEventHandler.class);
        SimpleMessageBus.getInstance().registerEventHandler(AttendeeNameChanged.class, AttendeeNameChangedEventHandler.class);
        SimpleMessageBus.getInstance().registerEventHandler(AttendeeDisabled.class, AttendeeDisabledEventHandler.class);
        SimpleMessageBus.getInstance().registerEventHandler(AttendeeEmailChanged.class, AttendeeEmailChangedEventHandler.class);
        SimpleMessageBus.getInstance().registerEventHandler(AttendeeChangeEmailConfirmed.class, AttendeeChangeEmailConfirmedEventHandler.class);
    }

}
