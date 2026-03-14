package com.dunnwr.taskmanagerapi.exceptions.task;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidTaskOwnerException extends DomainException {
    public InvalidTaskOwnerException(String message) {
        super(message, 400);
    }
}
