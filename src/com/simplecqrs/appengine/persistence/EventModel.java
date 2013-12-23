package com.simplecqrs.appengine.persistence;

/**
 * Simple event that is stored
 */
public class EventModel {
	private String kind = null;
	private String json = null;
	
	/**
	 * Default constructor
	 */
	public EventModel(){
		
	}
	
	/**
	 * Constructor to initialize the kind and the json data
	 * 
	 * @param kind
	 * @param json
	 */
	public EventModel(String kind, String json){
		this.kind = kind;
		this.json = json;
	}
	
	/**
	 * Get the kind
	 * @return
	 */
	public String getKind(){
		return this.kind;
	}
	
	/**
	 * Get the json data
	 * @return
	 */
	public String getJson(){
		return this.json;
	}
}
