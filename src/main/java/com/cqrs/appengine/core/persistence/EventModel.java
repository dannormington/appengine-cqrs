package com.cqrs.appengine.core.persistence;

/**
 * Simple event that is stored
 */
class EventModel {
    private String kind = null;
    private String json = null;
    private Long version = null;

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
     * @param version
     */
    public EventModel(String kind, String json, Long version){
        this.kind = kind;
        this.json = json;
        this.version = version;
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

    /**
     * Get the version
     * 
     * @return
     */
    public Long getVersion(){
        return this.version;
    }
}
