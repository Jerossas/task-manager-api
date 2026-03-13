package com.dunnwr.taskmanagerapi.exceptions.task;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidStatusException extends DomainException {
    public InvalidStatusException(String message) {
        super(message);
    }
}
