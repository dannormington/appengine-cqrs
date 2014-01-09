package com.simplecqrs.appengine.example;

/**
 * DTO use for GCE service call results
 */
public class ServiceResult {
    private String error = null;
    private boolean success = false;

    /**
     * Default constructor
     */
    public ServiceResult(){
        success = true;
    }

    /**
     * Constructor
     * 
     * @param exception
     */
    public ServiceResult(Exception exception){
        if(exception != null){
            this.error = exception.getMessage();
        }
    }

    /**
     * Constructor
     * 
     * @param error
     */
    public ServiceResult(String error){
        this.error = error;
    }

    /**
     * Returns true if the command was a success
     * 
     * @return
     */
    public boolean getSuccess(){
        return success;
    }

    /**
     * Get the error text
     * 
     * @return
     */
    public String getError(){
        return error;
    }
}
