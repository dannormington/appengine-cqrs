package com.simplecqrs.appengine.example.handlers;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.simplecqrs.appengine.example.domain.AttendeeRegistered;

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
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction transaction = datastoreService.beginTransaction();
		
        Key attendeeKey = KeyFactory.createKey("Attendee", event.getAttendeeId().toString());
        Entity attendee = null;
        
        try {
                attendee = datastoreService.get(attendeeKey);
                
                //do nothing if found. This is a new registration
                
        } catch (EntityNotFoundException e) {
        	
        	attendee = new Entity(attendeeKey);
        	attendee.setProperty("FirstName", event.getFirstName());
            attendee.setProperty("LastName", event.getLastName());
            datastoreService.put(attendee);
            transaction.commit();
            
        } finally {
        	if(transaction != null && transaction.isActive())
                transaction.rollback();
        }
	}
}
