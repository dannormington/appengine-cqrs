package com.simplecqrs.appengine.example;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.simplecqrs.appengine.example.commands.ChangeAttendeeName;
import com.simplecqrs.appengine.example.commands.RegisterAttendee;
import com.simplecqrs.appengine.example.commands.ResolveDuplicateEmail;
import com.simplecqrs.appengine.example.domain.AttendeeDisabled;
import com.simplecqrs.appengine.example.domain.AttendeeNameChanged;
import com.simplecqrs.appengine.example.domain.AttendeeRegistered;
import com.simplecqrs.appengine.example.handlers.commands.ChangeAttendeeNameCommandHandler;
import com.simplecqrs.appengine.example.handlers.commands.RegisterAttendeeCommandHandler;
import com.simplecqrs.appengine.example.handlers.commands.ResolveDuplicateEmailCommandHandler;
import com.simplecqrs.appengine.example.handlers.events.AttendeeDisabledEventHandler;
import com.simplecqrs.appengine.example.handlers.events.AttendeeNameChangedEventHandler;
import com.simplecqrs.appengine.example.handlers.events.AttendeeRegisteredEventHandler;
import com.simplecqrs.appengine.messaging.SimpleMessageBus;

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
		
		SimpleMessageBus.getInstance().registerEventHandler(AttendeeRegistered.class, AttendeeRegisteredEventHandler.class);
		SimpleMessageBus.getInstance().registerEventHandler(AttendeeNameChanged.class, AttendeeNameChangedEventHandler.class);
		SimpleMessageBus.getInstance().registerEventHandler(AttendeeDisabled.class, AttendeeDisabledEventHandler.class);
	}

}
