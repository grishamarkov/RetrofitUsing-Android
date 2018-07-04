package com.n2me.androidtv.common.event;

public class ServiceErrorEvent {

    private String errorMessage = "";

    public ServiceErrorEvent(String message) {
        errorMessage = message;
    }

    public String getMessage() {
        return errorMessage;
    }
}