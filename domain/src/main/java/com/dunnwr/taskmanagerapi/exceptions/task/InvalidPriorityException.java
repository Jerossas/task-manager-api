package com.dunnwr.taskmanagerapi.exceptions.task;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidPriorityException extends DomainException {
    public InvalidPriorityException(String message) {
        super(message);
    }
}
