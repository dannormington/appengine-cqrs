package com.cqrs.appengine.sample.handlers.events;

import com.cqrs.appengine.core.messaging.EventHandler;
import com.cqrs.appengine.sample.MessageLog;
import com.cqrs.appengine.sample.domain.AttendeeChangeEmailConfirmed;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class AttendeeChangeEmailConfirmedEventHandler extends EventHandler<AttendeeChangeEmailConfirmed> {

	private static final long serialVersionUID = 1L;

	public AttendeeChangeEmailConfirmedEventHandler(AttendeeChangeEmailConfirmed event) {
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
            attendee.setProperty("Email", event.getEmail());
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
