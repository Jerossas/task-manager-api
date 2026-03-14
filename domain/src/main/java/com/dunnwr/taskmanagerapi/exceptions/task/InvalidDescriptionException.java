package com.dunnwr.taskmanagerapi.exceptions.task;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidDescriptionException extends DomainException {
    public InvalidDescriptionException(String message) {
        super(message, 400);
    }
}
