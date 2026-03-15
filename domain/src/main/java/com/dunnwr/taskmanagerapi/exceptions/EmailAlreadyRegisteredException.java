package com.dunnwr.taskmanagerapi.exceptions;

public class EmailAlreadyRegisteredException extends DomainException{

    private static final int HTTP_CODE = 409;

    public EmailAlreadyRegisteredException(String message) {
        super(message, HTTP_CODE);
    }
}
