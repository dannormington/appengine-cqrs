package com.cqrs.appengine.sample.domain;

import java.util.UUID;

import com.cqrs.appengine.core.domain.AggregateRootBase;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.exceptions.InvalidParametersException;

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
     * Current email address
     */
    private String email = null;

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
    public void changeName(String firstName, String lastName) throws InvalidParametersException, HydrationException {

        /*
         * Don't bother checking for parameter validity if the attendee isn't enabled
         */
    	if(!isEnabled) return;
    	
        /*
         * Only change state if the data is valid 
         */
        if(firstName != null && firstName.trim().length() > 0 && lastName != null && lastName.trim().length() > 0)
            applyChange(new AttendeeNameChanged(this.getId(), firstName.trim(), lastName.trim()));
        else
            throw new InvalidParametersException(this.getId(), "First and last names are required.");
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
    	if(!isEnabled) return;
    	
        applyChange(new AttendeeDisabled(this.getId(), reason));
    }
    
    /**
     * Change an attendee's email
     * 
     * @param email
     */
    public void changeEmail(String email) throws InvalidParametersException, HydrationException {
    	
    	if(!isEnabled) return;
    	
    	if(email != null && email.trim().length() > 0){
    		applyChange(new AttendeeEmailChanged(this.getId(), email.trim()));
    	}else{
    		throw new InvalidParametersException(this.getId(), "Email is required.");
    	}
    }
    
    /**
     * Confirm an email change request
     * 
     * @param confirmationId
     * @throws HydrationException 
     * @throws InvalidParametersException 
     */
    public void confirmChangeEmail(UUID confirmationId) throws HydrationException, InvalidParametersException{
    	
    	if(!isEnabled) return;
    	
    	if(confirmationId.equals(this.confirmationId)){
    		applyChange(new AttendeeChangeEmailConfirmed(this.getId(),confirmationId, email));
    	} else{
    		throw new InvalidParametersException(this.getId(), "The confirmation Ids do not match.");	
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
    public static Attendee create(UUID attendeeId, String email, String firstName, String lastName) throws InvalidParametersException, HydrationException{

        /*
         * Only create an instance if all of the data is valid
         */
        if(attendeeId != null && email != null && email.trim().length() > 0 && firstName != null && firstName.trim().length() > 0 && lastName != null && lastName.trim().length() > 0)
            return new Attendee(attendeeId, email.trim(), firstName.trim(), lastName.trim());
        
        throw new InvalidParametersException(attendeeId, "Attendee Id, Email, First Name, Last Name are required.");
    }
    
    /**
     * Apply the state change for the {@link AttendeeDisabled} event
     * 
     * @param event
     */
    @SuppressWarnings("unused")
    private void apply(AttendeeRegistered event){
        email = event.getEmail();
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
    	email = event.getEmail();
    }
    
    /**
     * Apply the state change for the {@link AttendeeChangeEmailConfirmed} event
     * 
     * @param event
     */
    @SuppressWarnings("unused")
	private void apply(AttendeeChangeEmailConfirmed event){
    	confirmationId = null;
    }
}
