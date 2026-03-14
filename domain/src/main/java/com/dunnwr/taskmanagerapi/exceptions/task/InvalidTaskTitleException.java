package com.dunnwr.taskmanagerapi.exceptions.task;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidTaskTitleException extends DomainException {
    public InvalidTaskTitleException(String message) {
        super(message, 400);
    }
}
