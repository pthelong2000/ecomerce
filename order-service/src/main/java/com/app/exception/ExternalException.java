package com.app.exception;

public class ExternalException extends RuntimeException{

    public ExternalException() {
    }

    public ExternalException(String message) {
        super(message);
    }
}
