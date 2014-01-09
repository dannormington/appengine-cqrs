package com.simplecqrs.appengine.example.handlers.commands;

import com.simplecqrs.appengine.example.commands.RegisterAttendee;
import com.simplecqrs.appengine.example.domain.Attendee;
import com.simplecqrs.appengine.example.handlers.Constants;
import com.simplecqrs.appengine.messaging.CommandHandler;
import com.simplecqrs.appengine.exceptions.EventCollisionException;
import com.simplecqrs.appengine.persistence.EventRepository;
import com.simplecqrs.appengine.exceptions.HydrationException;
import com.simplecqrs.appengine.persistence.Repository;

public class RegisterAttendeeCommandHandler implements CommandHandler<RegisterAttendee> {

    @Override
    public void handle(RegisterAttendee command) throws EventCollisionException, HydrationException {

        Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class, Constants.DOMAIN_EVENTS_PROCESSING_QUEUE);

        Attendee attendee = Attendee.create(command.getAttendeeId(), command.getEmail(), command.getFirstName(), command.getLastName());

        if(attendee != null){
            repository.save(attendee);
        }
    }
}
