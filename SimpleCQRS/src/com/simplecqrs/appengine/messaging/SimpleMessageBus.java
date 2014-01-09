package com.simplecqrs.appengine.messaging;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.simplecqrs.appengine.exceptions.HydrationException;
import com.simplecqrs.appengine.exceptions.EventCollisionException;

/**
 * Implementation of a simple message bus for publishing
 * events and executing commands
 */
public class SimpleMessageBus implements MessageBus {

    /**
     * List of command handlers per command
     */
    private Map<String, CommandHandler<? extends Command>> commandHandlers;

    /**
     * List of event handlers per event
     */
    private Map<String, List<Class<? extends EventHandler<? extends Event>>>> eventHandlers;

    /**
     * Constructor
     */
    private SimpleMessageBus(){
        commandHandlers = new HashMap<String, CommandHandler<? extends Command>>();
        eventHandlers = new HashMap<String, List<Class<? extends EventHandler<? extends Event>>>>();
    }

    /**
     * Get the singleton instance
     * @return
     */
    public static SimpleMessageBus getInstance(){
        return InstanceHolder.INSTANCE;
    }

    /**
     * Creates a new instance
     * 
     * @return
     */
    private static SimpleMessageBus create(){
        return new SimpleMessageBus();
    }

    /**
     * Class to contain the singleton
     */
    private static class InstanceHolder{
        public static final SimpleMessageBus INSTANCE = SimpleMessageBus.create();
    }

    @Override
    public <T extends Command> void registerCommandHandler(Class<T> aClass, CommandHandler<T> handler) {
        String key = aClass.getName();

        /*
         * Register only if not found. Commands *should*
         * only be registered to a single handler
         */
        if(!commandHandlers.containsKey(key)){
            commandHandlers.put(key, handler);
        }
    }

    @Override
    public <T extends Event, H extends EventHandler<T>> void registerEventHandler(Class<T> aClass, Class<H> handler) {

        String key = aClass.getName();

        if(!eventHandlers.containsKey(key)){
            eventHandlers.put(key, new ArrayList<Class<? extends EventHandler<? extends Event>>>());
        }

        eventHandlers.get(key).add(handler);
    }

    @Override
    public <T extends Event> void publish(T event) {
        publish(event, null);
    }

    /**
     * Publish an event asynchronously
     * 
     * @throws PublishEventException 
     */
    @Override
    public <T extends Event> void publish(T event, String queue) {

        ThreadExecutor executor = new ThreadExecutor();        
        executor.execute(new PublishTask(event,queue));
    }
    
    /**
     * Execute a command synchronously
     */
    @Override
    public <T extends Command> void send(T command) throws EventCollisionException, HydrationException {

        if(command == null || commandHandlers.isEmpty())
            return;

        String key = command.getClass().getName();

        if(!commandHandlers.containsKey(key))
            return;

        CommandHandler<? extends Command> handlerForType = commandHandlers.get(key);

        @SuppressWarnings("unchecked")
        CommandHandler<T> handler = (CommandHandler<T>) handlerForType;
        handler.handle(command);
    }
    
    /**
     * Runnable task used to publish an event asynchronously.
     */
    private class PublishTask implements Runnable{

        private String queue;
        private Event event;
        
        public PublishTask(Event event, String queue){
            this.queue = queue;
            this.event = event;
        }
        
        @Override
        public void run() {
            if(event == null || eventHandlers.isEmpty())
                return;

            String key = event.getClass().getName();

            if(!eventHandlers.containsKey(key))
                return;

            List<Class<? extends EventHandler<? extends Event>>> handlersForType = eventHandlers.get(key);

            List<DeferredTask> taskList = new ArrayList<DeferredTask>();

            /*
             * Loop through all of the event handlers for the event being published 
             * and attempt to instantiate the handlers through reflection.
             */
            for(Class<? extends EventHandler<? extends Event>> handler : handlersForType){

                try{
                    /*
                     * get a constructor that expects a single parameter value that matches the event's type
                     */
                    Constructor<? extends EventHandler<? extends Event>> constructor = handler.getDeclaredConstructor(event.getClass());
                    constructor.setAccessible(true);
                    taskList.add(constructor.newInstance(event));

                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    /*
                     * unable to create an instance of the handler.
                     * more than likely this is caused by the handler
                     * not implementing a constructor that takes a single
                     * parameter instance of the event.
                     * 
                     *  There are several possibilities for this situation
                     *  - Publish a new event
                     *  - Log the exception
                     *  - Send an email to an administrator
                     *  - etc..
                     */
                }
            }

            if(taskList.isEmpty())
                return;

            Queue taskQueue = null;

            if(queue != null)
                taskQueue = QueueFactory.getQueue(queue);
            else
                taskQueue = QueueFactory.getDefaultQueue();

            for(DeferredTask task : taskList){
                taskQueue.addAsync(TaskOptions.Builder.withPayload(task));
            }
        }
    }
}
