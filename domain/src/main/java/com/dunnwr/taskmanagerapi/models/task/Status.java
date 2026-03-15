package com.dunnwr.taskmanagerapi.models.task;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;

public enum Status {
    NEW, IN_PROGRESS, DONE;

    public static Status from(String value){
        if(value == null) {
            throw new InvalidFieldException("status", "Status cannot be null.");
        }

        try {
            return Status.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException exception){
            throw new InvalidFieldException("status", "Invalid status value: " + value);
        }
    }
}
