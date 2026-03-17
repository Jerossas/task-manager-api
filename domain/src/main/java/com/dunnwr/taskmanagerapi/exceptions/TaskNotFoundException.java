package com.dunnwr.taskmanagerapi.exceptions;

public class TaskNotFoundException extends  DomainException {

    private static final int HTTP_CODE = 404;

    public TaskNotFoundException(String message) {
        super(message, HTTP_CODE);
    }
}
