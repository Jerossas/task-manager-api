package com.dunnwr.taskmanagerapi.models.task;

import com.dunnwr.taskmanagerapi.exceptions.task.InvalidPriorityException;

public enum Priority {
    LOW, MEDIUM, HIGH;

    public static Priority from(String value){
        if (value == null) {
            throw new InvalidPriorityException("Priority cannot be null.");
        }
        try {
            return Priority.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPriorityException("Invalid priority value: " + value);
        }
    }
}
