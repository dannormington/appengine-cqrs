package com.simplecqrs.appengine.example;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.users.User;
import com.simplecqrs.appengine.example.services.AttendeeService;

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
	public AttendeeRegistrationResult register(RegisterAttendeeCommand command, User user) {
		
		AttendeeService service = new AttendeeService();
		return service.register(command.getAttendeeId(), command.getFirstName(), command.getLastName());
	}
}
