package com.cqrs.appengine.sample.handlers.events;

import java.util.List;

import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.messaging.EventHandler;
import com.cqrs.appengine.core.messaging.SimpleMessageBus;
import com.cqrs.appengine.sample.MessageLog;
import com.cqrs.appengine.sample.commands.ResolveDuplicateEmail;
import com.cqrs.appengine.sample.domain.AttendeeRegistered;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;

/**
 * Deferred task to handle an attendee registering
 */
public class AttendeeRegisteredEventHandler extends EventHandler<AttendeeRegistered>{

    private static final long serialVersionUID = 1L;

    public AttendeeRegisteredEventHandler(AttendeeRegistered event){
        super(event);
    }

    @Override
    public void run() {

        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

        /*
         * Run a query against the read store to see if the email has already been registered
         */
        Filter emailFilter = new FilterPredicate("Email", FilterOperator.EQUAL, event.getEmail());

        Query attendeeQuery = new Query("Attendee")
        .setFilter(emailFilter)
        .setKeysOnly();

        PreparedQuery preparedQuery = datastoreService.prepare(attendeeQuery);
        List<Entity> matchingEmails = preparedQuery.asList(FetchOptions.Builder.withDefaults());

        /*
         * Create an attendee key based upon the UUID
         */
        Key attendeeKey = KeyFactory.createKey("Attendee", event.getAttendeeId().toString());
        Entity attendee = null;

        Transaction transaction = datastoreService.beginTransaction();

        try {
            attendee = datastoreService.get(attendeeKey);

            //do nothing if found. This is a new registration

        } catch (EntityNotFoundException e) {

            boolean duplicateExists = matchingEmails != null && !matchingEmails.isEmpty();

            attendee = new Entity(attendeeKey);
            attendee.setProperty("FirstName", event.getFirstName());
            attendee.setProperty("LastName", event.getLastName());
            attendee.setProperty("Email", event.getEmail());
            attendee.setProperty("IsEnabled", !duplicateExists);
            datastoreService.put(attendee);
            transaction.commit();

            /*
             * If a match is found. Send a compensating command.
             * You'll notice we're still allowing the read model to 
             * be populated
             */
            if(duplicateExists){
                try {
                    SimpleMessageBus.getInstance().send(new ResolveDuplicateEmail(event.getAttendeeId()));
                } catch (EventCollisionException | HydrationException | AggregateNotFoundException commandException) {
                    MessageLog.log(commandException);
                    //may want to publish message to administrator
                }
            }

        } finally {
            if(transaction != null && transaction.isActive())
                transaction.rollback();
        }
    }
}
