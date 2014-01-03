package com.simplecqrs.appengine.example;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.simplecqrs.appengine.example.commands.ChangeAttendeeName;
import com.simplecqrs.appengine.example.commands.RegisterAttendee;
import com.simplecqrs.appengine.messaging.MessageBus;

@Api(
		name="thatconference",
		version = "v1",
		description = "That Conference Sample API",
		clientIds = {com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID}
		)
public class AttendeeApi {
	
	@ApiMethod(
		httpMethod = "POST",
		path = "register"
	)
	public ServiceResult register(RegisterAttendee command) {
		
		try{
			MessageBus.getInstance().send(command);
		}catch(Exception e){
			return new ServiceResult(e);
		}
		
		return new ServiceResult();
	}
	
	@ApiMethod(
			httpMethod = "POST",
			path = "changename"
		)
	public ServiceResult changeName(ChangeAttendeeName command) {
			
		try{
			MessageBus.getInstance().send(command);	
		}catch(Exception e){
			return new ServiceResult(e);
		}
		
		return new ServiceResult();
	}
}
