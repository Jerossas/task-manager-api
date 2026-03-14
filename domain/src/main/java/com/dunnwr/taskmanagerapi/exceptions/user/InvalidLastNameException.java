package com.dunnwr.taskmanagerapi.exceptions.user;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidLastNameException extends DomainException {
    public InvalidLastNameException(String message) {
        super(message, 400);
    }
}
