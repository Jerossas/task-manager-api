package com.dunnwr.taskmanagerapi.models.task;

import com.dunnwr.taskmanagerapi.exceptions.task.InvalidStatusException;

public enum Status {
    NEW, IN_PROGRESS, DONE;

    public static Status from(String value){
        if(value == null) {
            throw new InvalidStatusException("Status cannot be null.");
        }

        try {
            return Status.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException exception){
            throw new InvalidStatusException("Invalid status value: " + value);
        }
    }
}
