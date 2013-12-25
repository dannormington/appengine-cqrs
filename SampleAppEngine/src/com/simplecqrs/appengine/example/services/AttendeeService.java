package com.simplecqrs.appengine.example.services;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import com.simplecqrs.appengine.example.AttendeeRegistrationResult;
import com.simplecqrs.appengine.example.domain.Attendee;
import com.simplecqrs.appengine.persistence.EventCollisionException;
import com.simplecqrs.appengine.persistence.EventRepository;
import com.simplecqrs.appengine.persistence.Repository;

public class AttendeeService {

	public AttendeeRegistrationResult register(UUID attendeeId, String firstName, String lastName){
		
		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class);
		
		try {
			Attendee attendee = repository.getById(attendeeId);
			
			if(attendee == null){
				attendee = new Attendee(attendeeId, firstName, lastName);
				try {
					repository.save(attendee);
				} catch (EventCollisionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (NoSuchMethodException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new AttendeeRegistrationResult();
	}
}
