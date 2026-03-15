package com.dunnwr.taskmanagerapi.models.task;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;

public enum Priority {
    LOW, MEDIUM, HIGH;

    public static Priority from(String value){
        if (value == null) {
            throw new InvalidFieldException("priority", "Priority cannot be null.");
        }
        try {
            return Priority.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidFieldException("priority", "Invalid priority value: " + value);
        }
    }
}
