package com.dunnwr.taskmanagerapi.exceptions;

public class InvalidCredentialsException extends DomainException{

    private static final int HTTP_CODE = 401;

    public InvalidCredentialsException(String message) {
        super(message, HTTP_CODE);
    }
}
