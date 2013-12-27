package com.simplecqrs.appengine.example.services;

import java.util.UUID;

import com.simplecqrs.appengine.example.ServiceResult;
import com.simplecqrs.appengine.example.domain.Attendee;
import com.simplecqrs.appengine.messaging.MessageLog;
import com.simplecqrs.appengine.persistence.AggregateHydrationException;
import com.simplecqrs.appengine.persistence.EventCollisionException;
import com.simplecqrs.appengine.persistence.EventRepository;
import com.simplecqrs.appengine.persistence.Repository;

public class AttendeeService {

	public ServiceResult register(UUID attendeeId, String firstName, String lastName){
		
		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class);

		Attendee attendee = null;
		try {
			attendee = repository.getById(attendeeId);
		} catch (AggregateHydrationException e) {
			MessageLog.log(e);
			//modify the results to inform of failure
		}
		
		if(attendee == null){
			attendee = new Attendee(attendeeId, firstName, lastName);
			try {
				repository.save(attendee);
				return new ServiceResult();
			} catch (EventCollisionException e) {
				MessageLog.log(e);
				//modify the results to inform of failure
			}
		}
			
		return new ServiceResult("failure!!!");
	}
	
	public ServiceResult changeAttendeeName(UUID attendeeId, String firstName, String lastName){
		
		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class);
		
		Attendee attendee = null;
		try {
			attendee = repository.getById(attendeeId);
		} catch (AggregateHydrationException e) {
			MessageLog.log(e);
			//modify the results to inform of failure
		}
		
		if(attendee != null){
			attendee.changeName(firstName, lastName);
			try {
				repository.save(attendee);
				return new ServiceResult();
			} catch (EventCollisionException e) {
				MessageLog.log(e);
			}
		}

		return new ServiceResult("failure!!!");
	}
}
