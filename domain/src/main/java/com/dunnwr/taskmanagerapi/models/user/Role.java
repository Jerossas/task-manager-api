package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;

public enum Role {
    USER, ADMIN;

    public static Role from(String value){
        if (value == null) {
            throw new InvalidFieldException("role", "Role cannot be null.");
        }
        try {
            return Role.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidFieldException("role", "Invalid role value: " + value);
        }
    }
}
