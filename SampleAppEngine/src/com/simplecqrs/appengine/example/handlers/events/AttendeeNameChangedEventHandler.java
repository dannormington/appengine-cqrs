package com.simplecqrs.appengine.example.handlers.events;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.simplecqrs.appengine.example.domain.AttendeeNameChanged;
import com.simplecqrs.appengine.messaging.EventHandler;
import com.simplecqrs.appengine.example.MessageLog;

/**
 * Deferred task for handling an attendee name change
 */
public class AttendeeNameChangedEventHandler extends EventHandler<AttendeeNameChanged>{

	private static final long serialVersionUID = 1L;

	public AttendeeNameChangedEventHandler(AttendeeNameChanged event){
		super(event);
	}
	
	@Override
	public void run() {
		
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction transaction = datastoreService.beginTransaction();
		
        Key attendeeKey = KeyFactory.createKey("Attendee", event.getAttendeeId().toString());
        Entity attendee = null;
        
        try {
                attendee = datastoreService.get(attendeeKey);
                attendee.setProperty("FirstName", event.getFirstName());
                attendee.setProperty("LastName", event.getLastName());
                datastoreService.put(attendee);
                transaction.commit();
                
        } catch (EntityNotFoundException e) {
        	MessageLog.log(e);
        } finally {
        	if(transaction != null && transaction.isActive())
                transaction.rollback();
        }
	}
}
