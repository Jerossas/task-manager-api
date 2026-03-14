package com.dunnwr.taskmanagerapi.exceptions.user;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidEmailException extends DomainException {
    public InvalidEmailException(String message) {
        super(message, 400);
    }
}
