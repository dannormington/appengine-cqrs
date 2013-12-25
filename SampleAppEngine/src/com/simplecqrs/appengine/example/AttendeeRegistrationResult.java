package com.simplecqrs.appengine.example;

public class AttendeeRegistrationResult {
	
	private String error = null;
	private boolean success = false;
	
	public AttendeeRegistrationResult(){
		success = true;
	}
	
	public AttendeeRegistrationResult(String error){
		this.error = error;
		this.success = false;
	}
	
	public boolean getSuccess(){
		return success;
	}
	
	public String getError(){
		return error;
	}
}
