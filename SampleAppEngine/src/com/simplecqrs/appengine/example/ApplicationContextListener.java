package com.simplecqrs.appengine.example;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.simplecqrs.appengine.example.handlers.CommandHandlers;
import com.simplecqrs.appengine.example.handlers.EventHandlers;
import com.simplecqrs.appengine.messaging.MessageBus;

public class ApplicationContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// App Engine does not currently invoke this method.	
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		MessageBus.getInstance().registerCommands(new CommandHandlers());
		MessageBus.getInstance().registerEvents(new EventHandlers());
	}

}
