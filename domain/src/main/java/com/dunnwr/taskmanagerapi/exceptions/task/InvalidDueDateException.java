package com.dunnwr.taskmanagerapi.exceptions.task;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidDueDateException extends DomainException {
    public InvalidDueDateException(String message) {
        super(message);
    }
}
