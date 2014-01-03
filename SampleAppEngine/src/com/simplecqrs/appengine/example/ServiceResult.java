package com.simplecqrs.appengine.example;

public class ServiceResult {
	private String error = null;
	private boolean success = false;
	
	public ServiceResult(){
		success = true;
	}
	
	public ServiceResult(Exception exception){
		if(exception != null){
			this.error = exception.getMessage();
		}
	}
	
	public ServiceResult(String error){
		this.error = error;
	}
	
	public boolean getSuccess(){
		return success;
	}
	
	public String getError(){
		return error;
	}
}
