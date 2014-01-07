package com.simplecqrs.appengine.example.domain;

import java.util.UUID;

import com.simplecqrs.appengine.domain.AggregateRootBase;

/**
 * Class that represents an attendee at a conference
 */
public class Attendee extends AggregateRootBase {
	
	/**
	 * Flag that determines if the attendee is enabled
	 */
	private boolean isEnabled = true;
	
	/**
	 * Constructor used when hydrating
	 * 
	 * @param attendeeId
	 */
	public Attendee(UUID attendeeId){
		super(attendeeId);
	}
	
	/**
	 * Constructor used when creating a new attendee
	 * 
	 * @param attendeeId
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @throws IllegalArgumentException
	 */
	private Attendee(UUID attendeeId, String email, String firstName, String lastName)  throws IllegalArgumentException {
		this(attendeeId);

		applyChange(new AttendeeRegistered(attendeeId, email, firstName, lastName));
	}
	
	/**
	 * Change the attendee's name
	 * 
	 * @param firstName
	 * @param lastName
	 * @throws IllegalArgumentException
	 */
	public void changeName(String firstName, String lastName) throws IllegalArgumentException {
		
		/*
		 * Don't bother checking for parameter validity if the attendee isn't enabled
		 */
		if(!isEnabled)
			return;
		/*
		 * Only change state if the data is valid 
		 */
		if(firstName != null && lastName != null)
			applyChange(new AttendeeNameChanged(this.getId(), firstName, lastName));
		else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Disable the attendee
	 * 
	 * @param reason
	 */
	public void disable(DisableReason reason){

		/*
		 * Only change if the attendee is enabled
		 */
		if(isEnabled)
			applyChange(new AttendeeDisabled(this.getId(), reason));
	}
	
	/**
	 * Create a new attendee. Returns null if the data is invalid
	 * 
	 * @param attendeeId
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public static Attendee create(UUID attendeeId, String email, String firstName, String lastName){

		/*
		 * Only create an instance if all of the data is valid
		 */
		if(attendeeId != null && email != null && firstName != null && lastName != null)
			return new Attendee(attendeeId, email, firstName, lastName);
		
		return null;
	}
	
	/**
	 * Apply the state change for the {@link AttendeeDisabled} event
	 * 
	 * @param event
	 */
	@SuppressWarnings("unused")
	private void apply(AttendeeDisabled event){
		isEnabled = false;
	}
}
