package com.simplecqrs.appengine.example;

public class ServiceResult {
	private String error = null;
	private boolean success = false;
	
	public ServiceResult(){
		success = true;
	}
	
	public ServiceResult(String error){
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
