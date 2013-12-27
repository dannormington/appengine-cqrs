package com.simplecqrs.appengine.example.services;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import com.simplecqrs.appengine.example.ServiceResult;
import com.simplecqrs.appengine.example.domain.Attendee;
import com.simplecqrs.appengine.messaging.MessageLog;
import com.simplecqrs.appengine.persistence.EventCollisionException;
import com.simplecqrs.appengine.persistence.EventRepository;
import com.simplecqrs.appengine.persistence.Repository;

public class AttendeeService {

	public ServiceResult register(UUID attendeeId, String firstName, String lastName){
		
		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class);
		
		try {
			Attendee attendee = repository.getById(attendeeId);
			
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
			
		} catch (NoSuchMethodException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			MessageLog.log(e);
			//modify the results to inform of failure
		}
		
		return new ServiceResult("failure!!!");
	}
	
	public ServiceResult changeAttendeeName(UUID attendeeId, String firstName, String lastName){
		
		Repository<Attendee> repository = new EventRepository<Attendee>(Attendee.class);
		
		try {
			Attendee attendee = repository.getById(attendeeId);
			
			if(attendee != null){
				attendee.changeName(firstName, lastName);
				try {
					repository.save(attendee);
					return new ServiceResult();
				} catch (EventCollisionException e) {
					MessageLog.log(e);
				}
			}
			
		} catch (NoSuchMethodException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			MessageLog.log(e);
			//modify the results to inform of failure
		}
		
		return new ServiceResult("failure!!!");
	}
}
