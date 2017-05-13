package com.cqrs.appengine.sample;

import com.cqrs.appengine.core.exceptions.AggregateNotFoundException;
import com.cqrs.appengine.core.exceptions.EventCollisionException;
import com.cqrs.appengine.core.exceptions.HydrationException;
import com.cqrs.appengine.core.messaging.Command;
import com.cqrs.appengine.core.messaging.SimpleMessageBus;
import com.cqrs.appengine.sample.commands.ChangeAttendeeEmail;
import com.cqrs.appengine.sample.commands.ChangeAttendeeName;
import com.cqrs.appengine.sample.commands.ConfirmChangeEmail;
import com.cqrs.appengine.sample.commands.RegisterAttendee;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;

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
    public void register(RegisterAttendee command) throws ConflictException, NotFoundException, BadRequestException {
    	processCommand(command);
    }

    @ApiMethod(
            httpMethod = "POST",
            path = "changename"
            )
    public void changeName(ChangeAttendeeName command) throws ConflictException, NotFoundException, BadRequestException  {
        processCommand(command);
    }
    
    @ApiMethod(
            httpMethod = "POST",
            path = "changeemail"
            )
    public void changeEmail(ChangeAttendeeEmail command) throws ConflictException, NotFoundException, BadRequestException  {
        processCommand(command);
    }
    
    @ApiMethod(
            httpMethod = "POST",
            path = "confirmchangeemail"
            )
    public void confirmChangeEmail(ConfirmChangeEmail command) throws ConflictException, NotFoundException, BadRequestException  {
        processCommand(command);
    }
    
    private void processCommand(Command command) throws ConflictException, NotFoundException, BadRequestException {
    	try{
            SimpleMessageBus.getInstance().send(command);	
        }catch(EventCollisionException e){
            throw new ConflictException(e.getMessage());
        }catch(HydrationException | AggregateNotFoundException e){
        	throw new NotFoundException(e.getMessage());
        }catch(Exception e){
        	throw new BadRequestException(e.getMessage());
        }
    }
    
}
