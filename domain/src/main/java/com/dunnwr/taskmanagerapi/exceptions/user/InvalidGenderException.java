package com.dunnwr.taskmanagerapi.exceptions.user;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidGenderException extends DomainException {
    public InvalidGenderException(String message) {
        super(message, 400);
    }
}
