package com.cqrs.appengine.sample.domain;

/**
 * Enumeration representing the reasons for
 * an attendee being disabled
 */
public enum DisableReason {
    DUPLICATE (1);

    private int reason;

    DisableReason(int reason){
        this.reason = reason;
    }

    public int getReason(){
        return reason;
    }
};
