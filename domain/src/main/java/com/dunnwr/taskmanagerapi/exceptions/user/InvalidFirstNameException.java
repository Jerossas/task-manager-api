package com.dunnwr.taskmanagerapi.exceptions.user;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidFirstNameException extends DomainException {
    public InvalidFirstNameException(String message) {
        super(message);
    }
}
