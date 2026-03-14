package com.dunnwr.taskmanagerapi.exceptions.user;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidPasswordException extends DomainException {
    public InvalidPasswordException(String message) {
        super(message, 400);
    }
}
