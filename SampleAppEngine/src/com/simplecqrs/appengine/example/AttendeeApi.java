package com.simplecqrs.appengine.example;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
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
	public ServiceResult register(RegisterAttendeeCommand command) {
		
		AttendeeService service = new AttendeeService();
		return service.register(command.getAttendeeId(), command.getFirstName(), command.getLastName());
	}
	
	@ApiMethod(
			httpMethod = "POST",
			path = "changename"
		)
		public ServiceResult changeName(ChangeAttendeeNameCommand command) {
			
			AttendeeService service = new AttendeeService();
			return service.changeAttendeeName(command.getAttendeeId(), command.getFirstName(), command.getLastName());
		}
}
