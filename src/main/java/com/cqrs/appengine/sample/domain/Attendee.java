package com.cqrs.appengine.sample.domain;

import java.util.UUID;

import com.cqrs.appengine.core.domain.AggregateRootBase;
import com.cqrs.appengine.core.exceptions.HydrationException;

/**
 * Class that represents an attendee at a conference
 */
public class Attendee extends AggregateRootBase {

    /**
     * Flag that determines if the attendee is enabled
     */
    private boolean isEnabled = true;
    
    /**
     * Id used to confirm an email change request
     */
    private UUID confirmationId = null;
    
    
    /**
     * Email address the user has requested a change to
     */
    private String unconfirmedEmail = null;

    /**
     * Constructor used when hydrating
     * 
     * @param attendeeId
     */
    private Attendee(UUID attendeeId){
        super(attendeeId);
    }

    /**
     * Constructor used when creating a new attendee
     * 
     * @param attendeeId
     * @param email
     * @param firstName
     * @param lastName
     * @throws HydrationException 
     */
    private Attendee(UUID attendeeId, String email, String firstName, String lastName) throws HydrationException {
        this(attendeeId);

        applyChange(new AttendeeRegistered(attendeeId, email, firstName, lastName));
    }

    /**
     * Change the attendee's name
     * 
     * @param firstName
     * @param lastName
     * @throws IllegalArgumentException
     * @throws HydrationException 
     */
    public void changeName(String firstName, String lastName) throws HydrationException {

        /*
         * Don't bother checking for parameter validity if the attendee isn't enabled
         */
    	if(!isEnabled) throw new IllegalStateException("Operation not allowed. The attendee is disabled");

        /*
         * Only change state if the data is valid 
         */
        if(firstName != null && firstName.trim().length() > 0 && lastName != null && lastName.trim().length() > 0) {
        	applyChange(new AttendeeNameChanged(this.getId(), firstName.trim(), lastName.trim()));
        }
        else {
        	throw new IllegalArgumentException("First and last names are required.");
        }
            
    }

    /**
     * Disable the attendee
     * 
     * @param reason
     * @throws HydrationException 
     */
    public void disable(DisableReason reason) throws HydrationException{

        /*
         * Only change if the attendee is enabled
         */
    	if(!isEnabled) throw new IllegalStateException("Operation not allowed. The attendee is disabled");
    	
        applyChange(new AttendeeDisabled(this.getId(), reason));
    }
    
    /**
     * Change an attendee's email
     * 
     * @param email
     */
    public void changeEmail(String email) throws HydrationException {
    	
    	if(!isEnabled) throw new IllegalStateException("Operation not allowed. The attendee is disabled");
    	
    	if(email != null && email.trim().length() > 0) {
    		applyChange(new AttendeeEmailChanged(this.getId(), email.trim()));
    	} else {
    		throw new IllegalArgumentException("Email is required.");
    	}
    }
    
    /**
     * Confirm an email change request
     * 
     * @param confirmationId
     * @throws HydrationException 
     * @throws InvalidParametersException 
     */
    public void confirmChangeEmail(UUID confirmationId) throws HydrationException {
    	
    	if(!isEnabled) throw new IllegalStateException("Operation not allowed. The attendee is disabled");
    	
    	if(confirmationId.equals(this.confirmationId)) {
    		applyChange(new AttendeeChangeEmailConfirmed(this.getId(), confirmationId, unconfirmedEmail));
    	} else {
    		throw new IllegalArgumentException("The confirmation Ids do not match.");	
    	}
    }

    /**
     * Create a new attendee. Returns null if the data is invalid
     * 
     * @param attendeeId
     * @param email
     * @param firstName
     * @param lastName
     * @return
     * @throws HydrationException 
     */
    public static Attendee create(UUID attendeeId, String email, String firstName, String lastName) throws HydrationException {

        /*
         * Only create an instance if all of the data is valid
         */
        if(attendeeId != null && email != null && email.trim().length() > 0 && firstName != null && firstName.trim().length() > 0 && lastName != null && lastName.trim().length() > 0) {
        	return new Attendee(attendeeId, email.trim(), firstName.trim(), lastName.trim());
        }
            
        throw new IllegalArgumentException("Attendee Id, Email, First Name, Last Name are required.");
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
    
    /**
     * Apply the state change for the {@link AttendeeEmailChanged} event
     * 
     * @param event
     */
    @SuppressWarnings("unused")
	private void apply(AttendeeEmailChanged event){
    	confirmationId = event.getConfirmationId();
    	unconfirmedEmail = event.getEmail();
    }
    
    /**
     * Apply the state change for the {@link AttendeeChangeEmailConfirmed} event
     * 
     * @param event
     */
    @SuppressWarnings("unused")
	private void apply(AttendeeChangeEmailConfirmed event){
    	confirmationId = null;
    	unconfirmedEmail = null;
    }
}
