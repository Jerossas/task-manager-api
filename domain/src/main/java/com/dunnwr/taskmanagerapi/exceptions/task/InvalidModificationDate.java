package com.dunnwr.taskmanagerapi.exceptions.task;

import com.dunnwr.taskmanagerapi.exceptions.DomainException;

public class InvalidModificationDate extends DomainException {
    public InvalidModificationDate(String message) {
        super(message, 400);
    }
}
