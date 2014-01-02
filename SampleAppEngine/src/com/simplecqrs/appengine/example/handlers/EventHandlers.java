package com.simplecqrs.appengine.example.handlers;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.common.eventbus.Subscribe;
import com.simplecqrs.appengine.example.domain.AttendeeNameChanged;
import com.simplecqrs.appengine.example.domain.AttendeeRegistered;

/**
 * Handles all domain events. Since there is a 60 second execution time for all
 * App engine requests we will push the handling to a deferred task. Another option
 * would be to use a push queue as well.
 */
public class EventHandlers {
	
	private static final String DOMAIN_EVENTS_PROCESSING_QUEUE = "domain-events-processing";
	
	private Queue queue = null;
	
	public EventHandlers(){
		/*
		 *  Queues can be defined in the queue.xml file located in the
		 *  war/WEB-INF/ directory
		 */
		queue = QueueFactory.getQueue(DOMAIN_EVENTS_PROCESSING_QUEUE);
	}
	
	@Subscribe
	public void handle(AttendeeRegistered event){
		queue.addAsync(TaskOptions.Builder.withPayload(new HandleAttendeeRegisteredTask(event)));
	}
	
	@Subscribe
	public void handle(AttendeeNameChanged event){
        queue.addAsync(TaskOptions.Builder.withPayload(new HandleAttendeeNameChangedTask(event)));
	}
}




